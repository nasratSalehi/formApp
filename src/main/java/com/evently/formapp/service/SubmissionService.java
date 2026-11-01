package com.evently.formapp.service;

import com.evently.formapp.dto.CreateSubmissionRequest;
import com.evently.formapp.dto.SubmissionResponse;
import com.evently.formapp.exception.NotFoundException;
import com.evently.formapp.model.Form;
import com.evently.formapp.model.Submission;
import com.evently.formapp.repository.FormRepository;
import com.evently.formapp.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final FormRepository formRepository;
    private final SubmissionValidatorService submissionValidatorService;


    public SubmissionResponse createSubmission(UUID formUuid, CreateSubmissionRequest request) {
        Form form = formRepository.findById(formUuid).orElseThrow(() -> new NotFoundException("Form not found"));

        submissionValidatorService.validateSubmission(form, request.answers());
        Submission submission = new Submission(form, request.answers(), LocalDateTime.now());
        Submission savedSubmission = submissionRepository.save(submission);
        return mapToResponse(savedSubmission);
    }

    public List<SubmissionResponse> findAllSubmissions(UUID formUuid) {
        if(!formRepository.existsById(formUuid)){
            throw new NotFoundException("Form not found");
        }
        List<Submission> submissions = submissionRepository.findByFormUuid(formUuid);
        return submissions.stream().map(this::mapToResponse).toList();
    }

    private SubmissionResponse mapToResponse(Submission submission){
        return new SubmissionResponse(
                submission.getUuid(),
                submission.getForm().getUuid(),
                submission.getAnswers(),
                submission.getSubmittedAt()
        );
    }
}

