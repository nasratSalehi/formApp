package com.evently.formapp.dto;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record SubmissionResponse(UUID uuid,
                                 UUID formUuid,
                                 Map<String, Object> answers,
                                 Date submissionDate) {
}
