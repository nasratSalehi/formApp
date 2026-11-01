package com.evently.formapp.service;

import com.evently.formapp.dto.CreateFormRequest;
import com.evently.formapp.dto.FieldRequest;
import com.evently.formapp.dto.FieldResponse;
import com.evently.formapp.dto.FormResponse;
import com.evently.formapp.exception.NotFoundException;
import com.evently.formapp.model.Event;
import com.evently.formapp.model.Form;
import com.evently.formapp.model.FormField;
import com.evently.formapp.repository.EventRepository;
import com.evently.formapp.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormService {


    private final FormRepository formRepository;
    private final EventRepository eventRepository;

    public FormResponse createForm(CreateFormRequest request) {
        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new NotFoundException("Event not found: " + request.eventId()));
        int position = 0;
        Form form = new Form(event, request.title());
        for (FieldRequest field : request.fields()) {
            form.addField(new FormField(form, field.name(), field.label(), field.fieldType(), field.required(), position++));
        }
        Form savedForm = formRepository.save(form);
        List<FieldResponse> fields = savedForm.getFields().stream()
                .map(field -> new FieldResponse(field.getName(), field.getLabel(),
                        field.getType(), field.isRequired())).toList();

        return new FormResponse(savedForm.getUuid(), savedForm.getEvent().getUuid(), savedForm.getTitle(), fields);
    }

    public List<FormResponse> findAllForms() {

        List<Form> forms = formRepository.findAll();
        return forms.stream().map(this::mapToResponse
        ).toList();
    }

    public FormResponse findByUuid(UUID uuid) {
        Form form = formRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Form not found: " + uuid));
        return mapToResponse(form);

    }

    public List<FormResponse> findFormsByEventUuid(UUID eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event not found: " + eventId);
        }
        List<Form> forms = formRepository.findByEventUuid(eventId);
        return forms.stream().map(this::mapToResponse).toList();
    }
    
    private FormResponse mapToResponse(Form form){
        return new FormResponse(
                form.getUuid(),
                form.getEvent().getUuid(),
                form.getTitle(),
                form.getFields().stream()
                        .map(field -> new FieldResponse(
                                field.getName(),
                                field.getLabel(),
                                field.getType(),
                                field.isRequired()
                        )).toList()
                
        );
    }


}
