package service;

import com.evently.formapp.dto.CreateSubmissionRequest;
import com.evently.formapp.dto.SubmissionResponse;
import com.evently.formapp.exception.NotFoundException;
import com.evently.formapp.model.Form;
import com.evently.formapp.model.Submission;
import com.evently.formapp.repository.FormRepository;
import com.evently.formapp.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final FormRepository formRepository;


    public SubmissionResponse createSubmission(UUID formUuid, CreateSubmissionRequest request) {
        Form form = formRepository.findByUuid(formUuid);
        if (form == null) {
            throw new NotFoundException("Form not found");
        }
        Submission submission = new Submission(form, request.answers(), new Date());
        return new SubmissionResponse(submission.getUuid(),
                submission.getForm().getUuid(),
                submission.getAnswers(),
                submission.getSubmittedAt());
    }

    public List<SubmissionResponse> findAllSubmissions(UUID formUuid) {
        if(!formRepository.existsById(formUuid)){
            throw new NotFoundException("Form not found");
        }
        List<Submission> submissions = submissionRepository.findByFormUuid(formUuid);
        return submissions.stream().map(submission -> new SubmissionResponse(
                submission.getUuid(),
                submission.getForm().getUuid(),
                submission.getAnswers(),
                submission.getSubmittedAt())).toList();
    }
}

