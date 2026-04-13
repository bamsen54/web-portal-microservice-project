package com.simon.wigellpadel.repository;

import com.simon.wigellpadel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);
    boolean existsByCourtIdAndBookingDateAndStartTime(Long courtId, LocalDate bookingDate, Integer startTime);
    boolean existsByCourtIdAndBookingDateAndStartTimeAndIdNot(Long courtId, LocalDate date, Integer startTime, Long id);
    List<Booking> findByCourtIdAndBookingDate(Long courtId, LocalDate date);

}
