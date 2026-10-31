package com.evently.formapp.dto;

import com.evently.formapp.enums.FieldType;
import com.evently.formapp.model.FormField;

import java.util.List;

public record FieldResponse(String name, String label, FieldType type,
                            boolean required) {
//    public static FieldResponse from(FormField formField){
//        return new FieldResponse(formField.getName(), formField.getLabel(),
//                formField.getType(), formField.isRequired());
//   }
}
