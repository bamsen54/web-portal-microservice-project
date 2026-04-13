package com.simon.wigellpadel.controller;

import com.simon.wigellpadel.dto.AvailabilityDto;
import com.simon.wigellpadel.service.BookingService;
import com.simon.wigellpadel.service.CourtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
public class UserAvailabilityController {

    private final BookingService bookingService;
    private final CourtService courtService;

    public UserAvailabilityController(BookingService bookingService, CourtService courtService) {
        this.bookingService = bookingService;
        this.courtService   = courtService;
    }

    @GetMapping
    public ResponseEntity<AvailabilityDto> getAvailability(@RequestParam Long courtId, @RequestParam LocalDate date) {
        List<Integer> availableTimes = bookingService.getAvailableTimes(courtId, date);
        String courtName    = courtService.findCourtById(courtId).courtName();
        return ResponseEntity.status(HttpStatus.OK).body( new AvailabilityDto(courtId, courtName, date, availableTimes));
    }
}