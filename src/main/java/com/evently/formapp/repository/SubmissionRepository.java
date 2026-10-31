package com.evently.formapp.repository;

import com.evently.formapp.model.Form;
import com.evently.formapp.model.Submission;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository {
    List<Submission> findByFormId(UUID formId);
    List<Submission> findByFormTitle(String formTitle);
}
