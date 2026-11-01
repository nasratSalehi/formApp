package com.evently.formapp.service;

import com.evently.formapp.exception.ValidationException;
import com.evently.formapp.model.Form;
import com.evently.formapp.model.FormField;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class SubmissionValidatorService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public void validateSubmission(Form form, Map<String, Object> answers) {
        Set<String> existingFields = new HashSet<>();
        List<String> errors = new ArrayList<>();

        for (FormField field : form.getFields()) {
            Object value = answers.get(field.getName());
            existingFields.add(field.getName());
            if (isEmpty(value)) {
                if (field.isRequired()) {
                    errors.add(field.getName() + " is required");
                }
                continue;
            }
            if(value != null){
                String error = checkType(field, value);
                if(error != null){
                    errors.add(error);
                }
            }
        }
        for (String key : answers.keySet()) {
            if (!existingFields.contains(key)) {
                errors.add(key + " is not a valid field");
            }
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    private String checkType(FormField field, Object value) {
        switch (field.getType()) {
            case TEXT:
                if (!(value instanceof String)) {
                    return field.getName() + " must be a string";
                }
                break;
            case NUMBER:
                if (!(value instanceof Number)) {
                    return field.getName() + " must be a number";
                }
                break;
            case BOOLEAN:
                if (!(value instanceof Boolean)) {
                    return field.getName() + " must be a boolean";
                }
                break;
            case DATE:
                if (!isDate(value)) {
                    return field.getName() + " must be a date";
                }
                break;
            case EMAIL:
                if(!isValidEmail(value)){
                    return field.getName() + " must be a valid email";
                }
                break;
        }
        return null;
    }

    public boolean isEmpty(Object value) {
        return value == null || (value instanceof String s && s.isBlank());
    }

    public boolean isValidEmail(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        String email = (String) value;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isDate(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        String text = (String) value;
        try{
            LocalDate.parse(text);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }

    }
}
