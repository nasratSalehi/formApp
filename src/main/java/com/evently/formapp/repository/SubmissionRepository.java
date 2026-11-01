package com.evently.formapp.repository;
import com.evently.formapp.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findByFormUuid(UUID formId);
    List<Submission> findByFormTitle(String formTitle);
}
