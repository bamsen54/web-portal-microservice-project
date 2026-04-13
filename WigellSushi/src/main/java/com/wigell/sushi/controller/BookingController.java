package com.wigell.sushi.controller;

import com.wigell.sushi.dto.BookingDTO;
import com.wigell.sushi.entity.Booking;
import com.wigell.sushi.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDTO> getAllBookings(@RequestParam(required = false) Integer customerId) {
        if (customerId != null) {
            return bookingService.getBookingsByCustomerId(customerId);
        }
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable int id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Booking booking) {
        BookingDTO saved = bookingService.createBooking(booking);
        return ResponseEntity.created(URI.create("/api/v1/bookings/" + saved.getId())).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable int id, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}