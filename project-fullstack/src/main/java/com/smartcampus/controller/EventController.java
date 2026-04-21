package com.smartcampus.controller;

import com.smartcampus.model.Registration;
import com.smartcampus.model.Event;
import com.smartcampus.model.User;
import com.smartcampus.service.EventService;
import com.smartcampus.service.RegistrationService;
import com.smartcampus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/events")
    public String listEvents(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("events", eventService.searchEvents(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("events", eventService.getAllEvents());
        }
        return "events";
    }

    @GetMapping("/events/register/{id}")
    public String showRegistrationForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            redirectAttributes.addFlashAttribute("error", "Event not found!");
            return "redirect:/events";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setStudentName(user.getName()); // prefill name
        
        model.addAttribute("registration", registration);
        model.addAttribute("event", event);
        return "registration-form";
    }

    @PostMapping("/events/register/{id}")
    public String registerForEvent(@PathVariable("id") Long id, @ModelAttribute("registration") Registration registration, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Event event = eventService.getEventById(id);
        
        if (event == null) {
            redirectAttributes.addFlashAttribute("error", "Event not found!");
            return "redirect:/events";
        }

        if (event.getTotalOpenings() != null && event.getRemainingOpenings() <= 0) {
            redirectAttributes.addFlashAttribute("error", "Sorry, this event is already full!");
            return "redirect:/events";
        }

        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(java.time.LocalDateTime.now());
        
        // Auto-assign payment status for now or assume it's set in form
        if (registration.getPaymentStatus() == null || registration.getPaymentStatus().isEmpty()) {
            registration.setPaymentStatus("Completed");
        }

        boolean success = registrationService.registerUserToEvent(registration);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Successfully registered for the event!");
        } else {
            redirectAttributes.addFlashAttribute("error", "You are already registered for this event.");
        }
        
        return "redirect:/events";
    }

    // Admin Mappings
    @GetMapping("/admin/events/new")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    @PostMapping("/admin/events")
    public String saveEvent(@Valid @ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "event-form";
        }
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/admin/events/edit/{id}")
    public String editEventForm(@PathVariable("id") Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "event-form";
    }

    @PostMapping("/admin/events/{id}")
    public String updateEvent(@PathVariable("id") Long id, @Valid @ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "event-form";
        }
        Event existingEvent = eventService.getEventById(id);
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDepartment(event.getDepartment());
        existingEvent.setEventType(event.getEventType());
        existingEvent.setDate(event.getDate());
        existingEvent.setTime(event.getTime());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setParticipationType(event.getParticipationType());
        existingEvent.setFeeAmount(event.getFeeAmount());
        existingEvent.setTotalOpenings(event.getTotalOpenings());
        
        eventService.saveEvent(existingEvent);
        return "redirect:/events";
    }

    @GetMapping("/admin/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}
