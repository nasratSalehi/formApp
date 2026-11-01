package com.evently.formapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateFormRequest(
        @NotNull( message = "Event id cannot be null")
        UUID eventId,
        @NotBlank( message = "Title cannot be blank")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,
        @NotEmpty( message = "A form should have at least one field")
        List<@Valid FieldRequest> fields) {
}
