package com.evently.formapp.dto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public record CreateFormRequest(UUID eventId, String title, List<@Valid FieldRequest> fields) {
}
