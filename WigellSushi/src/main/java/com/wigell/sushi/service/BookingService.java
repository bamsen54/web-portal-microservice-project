package com.wigell.sushi.service;

import com.wigell.sushi.dto.BookingDTO;
import com.wigell.sushi.dto.CustomerDTO;
import com.wigell.sushi.dto.DishDTO;
import com.wigell.sushi.dto.RoomDTO;
import com.wigell.sushi.entity.Booking;
import com.wigell.sushi.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private BookingDTO toDTO(Booking b) {
        BookingDTO dto = new BookingDTO();
        dto.setId(b.getId());
        dto.setDate(b.getDate());
        dto.setNumGuests(b.getNumGuests());
        dto.setTotalPriceSek(b.getTotalPriceSek());
        dto.setTotalPriceJpy(b.getTotalPriceJpy());

        if (b.getCustomer() != null) {
            CustomerDTO c = new CustomerDTO();
            c.setId(b.getCustomer().getId());
            c.setUsername(b.getCustomer().getUsername());
            c.setFirstName(b.getCustomer().getFirstName());
            c.setLastName(b.getCustomer().getLastName());
            dto.setCustomer(c);
        }

        if (b.getRoom() != null) {
            RoomDTO r = new RoomDTO();
            r.setId(b.getRoom().getId());
            r.setName(b.getRoom().getName());
            r.setMaxGuests(b.getRoom().getMaxGuests());
            r.setTechnicalEquipment(b.getRoom().getTechnicalEquipment());
            dto.setRoom(r);
        }

        if (b.getDishes() != null) {
            dto.setDishes(b.getDishes().stream().map(d -> {
                DishDTO dd = new DishDTO();
                dd.setId(d.getId());
                dd.setName(d.getName());
                dd.setDescription(d.getDescription());
                dd.setPriceSek(d.getPriceSek());
                return dd;
            }).toList());
        }

        return dto;
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::toDTO).toList();
    }

    public BookingDTO getBookingById(int id) {
        return bookingRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public List<BookingDTO> getBookingsByCustomerId(int customerId) {
        return bookingRepository.findByCustomerId(customerId).stream().map(this::toDTO).toList();
    }

    public BookingDTO createBooking(Booking booking) {
        Booking saved = bookingRepository.save(booking);
        logger.info("user created booking {}", saved.getId());
        return toDTO(saved);
    }

    public BookingDTO updateBooking(int id, Booking booking) {
        booking.setId(id);
        Booking saved = bookingRepository.save(booking);
        logger.info("user updated booking {}", id);
        return toDTO(saved);
    }

    public void deleteBooking(int id) {
        bookingRepository.deleteById(id);
        logger.info("admin deleted booking {}", id);
    }
}