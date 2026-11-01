package com.evently.formapp.dto;

import com.evently.formapp.enums.FieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FieldResponse(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 100, message = "field name must be between 3 and 100 characters")
        String name,
        @NotBlank(message = "Label cannot be blank")
        @Size(min = 3, max = 100, message = "field label must be between 3 and 100 characters")
        String label,
        @NotNull(message = "Field type cannot be null")
        FieldType type,
        boolean required) {
}
