package com.simon.wigellpadel.dto;

import java.time.LocalDate;
import java.util.List;

public record AvailabilityDto(
        Long courtId,
        String courtName,
        LocalDate date,
        List<Integer> availableTimes
) {}