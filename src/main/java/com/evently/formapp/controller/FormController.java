package com.evently.formapp.controller;

import com.evently.formapp.dto.CreateFormRequest;
import com.evently.formapp.dto.FormResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evently.formapp.service.FormService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forms")
@AllArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping
    public ResponseEntity<FormResponse> createForm(@Valid @RequestBody CreateFormRequest request) {
        FormResponse formResponse = formService.createForm(request);
        URI location = URI.create("/api/forms/" + formResponse.id());
        return ResponseEntity.created(location).body(formResponse);
    }

    @GetMapping()
    public ResponseEntity<List<FormResponse>> findAllForms(){
        return ResponseEntity.ok(formService.findAllForms());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FormResponse> findFormByUuid(@PathVariable UUID uuid){
        return ResponseEntity.ok(formService.findByUuid(uuid));
    }
}
