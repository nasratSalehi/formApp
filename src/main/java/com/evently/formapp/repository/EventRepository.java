package com.evently.formapp.repository;

import com.evently.formapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Event findByName(String name);
    Event findByUuid(UUID uuid);
}
