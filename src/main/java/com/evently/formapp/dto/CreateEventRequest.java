package com.evently.formapp.dto;

import com.evently.formapp.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEventRequest(
        @NotBlank(message = "event name cannot be blank")
        String name,
        @NotNull(message = "event type cannot be null")
        EventType eventType) {

}
