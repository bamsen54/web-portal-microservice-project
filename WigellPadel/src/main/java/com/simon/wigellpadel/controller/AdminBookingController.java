package com.simon.wigellpadel.controller;

import com.simon.wigellpadel.dto.BookingDto;
import com.simon.wigellpadel.entity.Customer;
import com.simon.wigellpadel.service.BookingService;
import com.simon.wigellpadel.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class AdminBookingController {

    private BookingService bookingService;
    private CustomerService customerService;

    public  AdminBookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService  = bookingService;
        this.customerService = customerService;
    }

    // collision between ADMIN GET /api/bookings and USER /api/bookings?customerId={customerId}
    // USERS can only check their own bookings
    // ADMINS can check all bookinsgs via GET /api/bookings and GET /api/bookings?customerId={customerId}
    // where customerId can be any existing customer id
    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam(required = false) Long customerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && customerId == null)
            throw new AccessDeniedException("You must specify customerId");

        Customer loggedInCustomer = customerService.findCustomerEntityByUsername(auth.getName());
        Long loggedInCustomerId   = loggedInCustomer.getId();

        if (isAdmin && customerId == null)
            return ResponseEntity.ok(bookingService.findAllBookings());

        Long targetCustomerId = customerId != null ? customerId : loggedInCustomerId;

        if (!isAdmin && !targetCustomerId.equals(loggedInCustomerId))
            throw new AccessDeniedException("You can only view your own bookings");

        return ResponseEntity.ok(bookingService.findByCustomerId(targetCustomerId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.findBookingById(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {
        bookingService.delete(bookingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
