package com.simon.wigellpadel.service;

import com.simon.wigellpadel.dto.BookingDto;
import com.simon.wigellpadel.dto.PostBookingDto;
import com.simon.wigellpadel.entity.Booking;
import com.simon.wigellpadel.entity.Court;
import com.simon.wigellpadel.entity.Customer;
import com.simon.wigellpadel.exception.*;
import com.simon.wigellpadel.mapper.BookingMapper;
import com.simon.wigellpadel.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final CourtService courtService;
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Value("${app.exchange.sek-to-eur}")
    private double exchangeSekToEur;

    @Value("${app.exchange.eur-to-sek}")
    private double exchangeEurToSek;

    public BookingService(BookingRepository bookingRepository,  CustomerService customerService, CourtService courtService) {
        this.bookingRepository = bookingRepository;
        this.customerService   = customerService;
        this.courtService      = courtService;
    }

    public List<BookingDto> findAllBookings() {
        return bookingRepository.findAll().stream().map(BookingMapper::toDto).toList();
    }

    public List<BookingDto> findByCustomerId(Long customerId) {

        if(customerService.findById(customerId) == null)
            throw new CustomerDoesNotExistException(customerId);

        return bookingRepository.findByCustomerId(customerId).stream()
            .map(BookingMapper::toDto)
            .toList();
    }

    public BookingDto findBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingDoesNotExistException(bookingId));
        return BookingMapper.toDto(booking);
    }

    public BookingDto save(PostBookingDto dto) {

        Customer customer = null;
        Court court       = null;

        Authentication auth      = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = auth.getName();
        boolean principalIsAdmin          = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        try {

            // admins can create a booking for any customer
            if(principalIsAdmin && dto.customerId() != null)
                customer = customerService.findCustomerEntityById(dto.customerId());

            // non admins can only book for themselves, i.e. so we use the principal
            else
                customer = customerService.findCustomerEntityByUsername(principalUsername);

            court   = courtService.findCourtEntityById(dto.courtId());
        }

        catch (CustomerDoesNotExistException e) {
            logger.warn("customer with id: {} not found when saving booking - creation attempt rejected",  dto.customerId());
        }

        catch (CourtDoesNotExistException e) {
            logger.warn("court with id: {} not found when saving booking - creation attempt rejected",  dto.courtId());
        }

        if(customer == null) {
            String identifier = (dto.customerId() != null)
                    ? String.valueOf(dto.customerId())
                    : principalUsername;
            logger.error("Customer not found: {} - creation attempt rejected", identifier);
            throw new CustomerDoesNotExistException(identifier);
        }

        if(court == null) {
            throw new CourtDoesNotExistException(dto.courtId());
        }

        if (bookingRepository.existsByCourtIdAndBookingDateAndStartTime(dto.courtId(), dto.bookingDate(), dto.startTime())) {
            logger.warn("Court: " + dto.courtId() + " is already booked at: " + dto.bookingDate() + " " + dto.startTime() + ":00 - creation attempt rejected");
            throw new BookingConflictException("Court: " + dto.courtId() + " is already booked at: " + dto.bookingDate() + " " + dto.startTime() + ":00");
        }

        LocalDateTime bookingDateTime = LocalDateTime.of(dto.bookingDate(), LocalTime.of(dto.startTime(), 0));

        if (bookingDateTime.isBefore(LocalDateTime.now())) {
            logger.warn("Datetime is in the past - creation attempt rejected");
            throw new TimeMachineDoesNotExistException();
        }

        Booking booking = BookingMapper.fromPostBookingDto(dto, customer, court);

        double totalPriceSek = court.getPricePerHourSek();
        double totalPriceEur = totalPriceSek * exchangeSekToEur;

        booking.setTotalPriceSek(totalPriceSek);
        booking.setTotalPriceEur(totalPriceEur);

        bookingRepository.save(booking);

        logger.info("booking with id: {} saved successfully", booking.getId());

        return BookingMapper.toDto(booking);
    }

    public List<Integer> getAvailableTimes(Long courtId, LocalDate date) {

        if(courtService.findCourtEntityById(courtId) == null)
            throw new CourtDoesNotExistException(courtId);

        List<Integer> bookedTimes = bookingRepository.findByCourtIdAndBookingDate(courtId, date)
                .stream()
                .map(Booking::getStartTime)
                .toList();

        List<Integer> allTimes = Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);

        return allTimes.stream()
                .filter(time -> !bookedTimes.contains(time))
                .toList();
    }

    @Transactional
    public BookingDto patch(Long id, Map<String, Object> updates) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingDoesNotExistException(id));

        if (updates.containsKey("bookingDate")) {
            String dateString = (String) updates.get("bookingDate");
            booking.setBookingDate(LocalDate.parse(dateString));
        }

        if (updates.containsKey("startTime")) {
            Integer startTime = (Integer) updates.get("startTime");

            if(startTime < 8 || startTime > 21) {
                logger.warn("startTime must be between 8 and 21 - update attempt rejected");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startTime must be between 8 and 21");
            }

            booking.setStartTime(startTime);
        }

        if (updates.containsKey("numberOfPlayers")) {
            Integer numberOfPlayers = (Integer) updates.get("numberOfPlayers");

            if(numberOfPlayers < 1) {
                logger.warn("numberOfPlayers must be at least 1 - update attempt rejected");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "numberOfPlayers must be greater than 0");
            }

            booking.setNumberOfPlayers(numberOfPlayers);
        }

        if (updates.containsKey("courtId")) {
            Long courtId = ((Number) updates.get("courtId")).longValue();
            Court court = courtService.findCourtEntityById(courtId);
            booking.setCourt(court);

            double newPrice = court.getPricePerHourSek();
            booking.setTotalPriceSek(newPrice);
            booking.setTotalPriceEur(newPrice * exchangeSekToEur);
        }

        if (updates.containsKey("bookingDate") || updates.containsKey("startTime") || updates.containsKey("courtId")) {
            LocalDate date = updates.containsKey("bookingDate")
                    ? LocalDate.parse((String) updates.get("bookingDate"))
                    : booking.getBookingDate();

            Integer time = updates.containsKey("startTime")
                    ? (Integer) updates.get("startTime")
                    : booking.getStartTime();

            Long courtId = updates.containsKey("courtId")
                    ? ((Number) updates.get("courtId")).longValue()
                    : booking.getCourt().getId();

            LocalDateTime bookingDateTime = LocalDateTime.of(date, LocalTime.of(time, 0));
            if (bookingDateTime.isBefore(LocalDateTime.now())) {
                logger.warn("Datetime is in the past - update attempt rejected");
                throw new TimeMachineDoesNotExistException();
            }

            boolean bookingAlreadyExists = bookingRepository.existsByCourtIdAndBookingDateAndStartTimeAndIdNot(courtId, date, time, id);
            if (bookingAlreadyExists) {
                logger.warn("Court: " + courtId + " is already booked at: " + date + " " + time + ":00 - update attempt rejected");
                throw new BookingConflictException("Court: " + courtId + " is already booked at: " + date + " " + time + ":00");
            }
        }

        Booking saved = bookingRepository.save(booking);
        logger.info("Patched booking with id: {}", id);
        return BookingMapper.toDto(saved);
    }


    public void delete(Long id) {
        logger.info("Deleted booking with id: {}", id);
        bookingRepository.deleteById(id);
    }
}
