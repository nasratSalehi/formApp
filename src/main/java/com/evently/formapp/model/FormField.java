package com.evently.formapp.model;

import com.evently.formapp.enums.FieldType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class FormField {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "form_id")
    private Form form;

    @Column(nullable = false)
    private String name;          // key in the JSON, e.g. "email"

    @Column(nullable = false)
    private String label;         // shown to the user, e.g. "E-mail"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType type;

    public FormField(Form form, String name, String label, FieldType type, boolean required) {
        this.form = form;
        this.name = name;
        this.label = label;
        this.type = type;
        this.required = required;
    }

    private boolean required;

}
