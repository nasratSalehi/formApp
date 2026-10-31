package com.evently.formapp.dto;

import com.evently.formapp.enums.EventType;

import java.util.UUID;

public record EventResponse(UUID id, String name, EventType eventType) {
}
