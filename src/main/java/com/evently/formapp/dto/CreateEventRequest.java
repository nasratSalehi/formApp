package com.evently.formapp.dto;

import com.evently.formapp.enums.EventType;

public record CreateEventRequest(String name, EventType eventType) {

}
