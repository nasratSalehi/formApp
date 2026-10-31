package service;

import com.evently.formapp.dto.CreateEventRequest;
import com.evently.formapp.dto.EventResponse;
import com.evently.formapp.exception.NotFoundException;
import com.evently.formapp.model.Event;
import com.evently.formapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponse createEvent(CreateEventRequest request) {
        Event event = new Event(request.name(), request.eventType());
        Event savedEvent = eventRepository.save(event);

        return new EventResponse(savedEvent.getUuid(), savedEvent.getName(), savedEvent.getEventType());
    }

    public EventResponse findEventByUuid(UUID uuid) {
        Event event = eventRepository.findByUuid(uuid);
        if(event == null){
            throw new NotFoundException("Event not found");
        }
        return new EventResponse(event.getUuid(), event.getName(), event.getEventType());
    }

    public EventResponse findEventByName(String name) {
        Event event = eventRepository.findByName(name);
        if(event == null){
            throw new NotFoundException("Event not found");
        }
        return new EventResponse(event.getUuid(), event.getName(), event.getEventType());
    }

    public EventResponse findAllEvents() {
        return eventRepository.findAll().stream().map(event -> new EventResponse(
                event.getUuid(),
                event.getName(),
                event.getEventType())).findFirst().orElseThrow();
    }
}
