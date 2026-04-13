package com.simon.wigellpadel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PutCourtDto(

        @NotBlank(message        = "courtName cannot be blank")
        @Size(min = 1, message   = "courtName has to be at least 1 character long")
        @Size(max = 100, message = "courtName has to be at most 100 characters long")
        String courtName,

        @Positive(message = "pricePerHourSek must be a positive value")
        double pricePerHourSek
) {}