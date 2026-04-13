package com.simon.wigellpadel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostAddressDto(

        @NotBlank(message = "street cannot be blank")
        @Size(min = 1, message = "street has to be at least 1 character long")
        @Size(max = 100, message = "street has to be at most 100 characters long")
        String street,

        @NotBlank(message = "postalCode cannot be blank")
        @Size(min = 1, message = "postalCode has to be at least 1 character long")
        @Size(max = 25, message = "postalCode has to be at most 25 characters long")
        String postalCode,

        @NotBlank(message = "city cannot be blank")
        @Size(min = 1, message = "city has to be at least 1 character long")
        @Size(max = 100, message = "city has to be at most 100 characters long")
        String city
) {}