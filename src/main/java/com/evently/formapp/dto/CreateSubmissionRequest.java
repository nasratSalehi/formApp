package com.evently.formapp.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record CreateSubmissionRequest(@NotNull Map<String, Object> answers) {
}
