package com.smartcampus.controller;

import com.smartcampus.model.Event;
import com.smartcampus.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Event> events;
        if (keyword != null && !keyword.isEmpty()) {
            events = eventService.searchEvents(keyword);
        } else {
            events = eventService.getAllEvents();
        }
        return ResponseEntity.ok(events);
    }
}
