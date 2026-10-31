package controller;

import com.evently.formapp.dto.CreateEventRequest;
import com.evently.formapp.dto.EventResponse;
import com.evently.formapp.dto.FormResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EventService;
import service.FormService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final FormService formService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse eventResponse = eventService.createEvent(request);
        URI location = URI.create("/api/events/" + eventResponse.id());
        return ResponseEntity.created(location).body(eventResponse);
    }

    @GetMapping()
    public ResponseEntity<List<EventResponse>> findAllEvents(){
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EventResponse> findEventByUuid(@PathVariable UUID uuid){
        return ResponseEntity.ok(eventService.findEventByUuid(uuid));
    }

    @GetMapping("/{name}")
    public ResponseEntity<EventResponse> findEventByName(@PathVariable String name){
        return ResponseEntity.ok(eventService.findEventByName(name));
    }

    @GetMapping("/{eventUuid}/forms")
    public ResponseEntity<List<FormResponse>> getFormsForEvent(@PathVariable UUID eventUuid) {
        return ResponseEntity.ok(formService.findFormsByEventUuid(eventUuid));
    }
}
