package com.smartcampus.service;

import com.smartcampus.model.Event;
import com.smartcampus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        Optional<Event> optional = eventRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> searchEvents(String keyword) {
        return eventRepository.findByDepartmentContainingIgnoreCaseOrEventTypeContainingIgnoreCase(keyword, keyword);
    }
}
