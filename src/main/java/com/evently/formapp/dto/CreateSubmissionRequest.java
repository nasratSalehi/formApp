package com.evently.formapp.dto;
import jakarta.validation.constraints.NotEmpty;
import java.util.Map;

public record CreateSubmissionRequest(@NotEmpty Map<String, Object> answers) {
}
