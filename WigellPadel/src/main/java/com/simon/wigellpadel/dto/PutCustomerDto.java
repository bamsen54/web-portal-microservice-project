package com.simon.wigellpadel.dto;

import com.simon.wigellpadel.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PutCustomerDto(

        @NotBlank(message = "username cannot be blank")
        @Size(min = 1, message = "username has to be at least 1 character long")
        @Size(max = 100, message = "username has to be at most 100 characters long")
        String username,

        @NotBlank(message = "password cannot be blank")
        @Size(min = 1, message = "password has to be at least 1 character long")
        @Size(max = 100, message = "password has to be at most 100 characters long")
        String password,

        @NotNull(message = "role cannot be null")
        Role role,

        @NotBlank(message = "firstName cannot be blank")
        @Size(min = 1, message = "firstName has to be at least 1 character long")
        @Size(max = 100, message = "firstName has to be at most 100 characters long")
        String firstName,

        @NotBlank(message = "lastName cannot be blank")
        @Size(min = 1, message = "lastName has to be at least 1 character long")
        @Size(max = 100, message = "lastName has to be at most 100 characters long")
        String lastName
) {}