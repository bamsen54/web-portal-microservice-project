package com.simon.wigellpadel.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PostBookingDto(

        @NotNull(message = "customerId cannot be null")
        Long customerId,

        @NotNull(message = "courtId cannot be null")
        Long courtId,

        @NotNull(message = "bookingDate cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate bookingDate,

        @NotNull(message = "startTime cannot be null")
        @Min(value = 8, message = "startTime must be at least 8")
        @Max(value = 21, message = "startTime must be at most 21")
        Integer startTime,

        @NotNull(message = "numberOfPlayers cannot be null")
        @Positive(message = "numberOfPlayers must be at least 1")
        Integer numberOfPlayers
) {
}