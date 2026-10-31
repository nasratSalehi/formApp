package com.evently.formapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormField> fields = new ArrayList<>();

    public Form(Event event, String title, List<FormField> fields) {
        this.event = event;
        this.title = title;
        this.fields = fields;
    }

    public Form(Event event, String title) {
        this.event = event;
        this.title = title;
    }

    public void addField(FormField formField) {
        fields.add(formField);
    }
}
