package com.evently.formapp.dto;

import java.util.List;
import java.util.UUID;

public record FormResponse(UUID id, UUID eventId, String title, List<FieldResponse> fields) {
}
