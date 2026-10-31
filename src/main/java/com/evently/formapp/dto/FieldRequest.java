package com.evently.formapp.dto;

import com.evently.formapp.enums.FieldType;

public record FieldRequest(
        String name,
        String label,
        FieldType fieldType,
        boolean required
) {
}
