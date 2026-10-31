package com.evently.formapp.repository;

import com.evently.formapp.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, UUID> {
    List<Form> findByEventUuid(UUID eventId);
    Form findByUuid(UUID uuid);

}
