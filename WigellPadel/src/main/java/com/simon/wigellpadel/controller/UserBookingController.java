package com.simon.wigellpadel.controller;

import com.simon.wigellpadel.dto.BookingDto;
import com.simon.wigellpadel.dto.PatchBookingDto;
import com.simon.wigellpadel.dto.PostBookingDto;
import com.simon.wigellpadel.entity.Booking;
import com.simon.wigellpadel.mapper.BookingMapper;
import com.simon.wigellpadel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserBookingController {

    private BookingService bookingService;

    public UserBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/bookings")
    public ResponseEntity<BookingDto> postBooking(@Valid @RequestBody PostBookingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.save(dto));
    }

    @PatchMapping("/bookings/{bookingId}")
    public ResponseEntity<BookingDto> patchBooking(@PathVariable Long bookingId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(bookingService.patch(bookingId, updates));
    }
}
