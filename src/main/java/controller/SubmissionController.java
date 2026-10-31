package controller;

import com.evently.formapp.dto.CreateSubmissionRequest;
import com.evently.formapp.dto.SubmissionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SubmissionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms/{formUuid}/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(@PathVariable UUID formUuid,
                                                               @Valid @RequestBody CreateSubmissionRequest request){
        SubmissionResponse submissionResponse = submissionService.createSubmission(formUuid, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionResponse);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponse>> findAllSubmissions(@PathVariable UUID formUuid){
        return ResponseEntity.ok(submissionService.findAllSubmissions(formUuid));
    }


}
