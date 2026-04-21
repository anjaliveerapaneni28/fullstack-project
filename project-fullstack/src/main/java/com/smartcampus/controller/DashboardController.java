package com.smartcampus.controller;

import com.smartcampus.model.User;
import com.smartcampus.service.EventService;
import com.smartcampus.service.RegistrationService;
import com.smartcampus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private EventService eventService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", user);

        if (user.getRole().name().equals("ROLE_ADMIN")) {
            model.addAttribute("totalEvents", eventService.getAllEvents().size());
            model.addAttribute("allRegistrations", registrationService.getAllRegistrations());
            return "admin-dashboard";
        } else {
            model.addAttribute("registrations", registrationService.getUserRegistrations(user));
            return "student-dashboard";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}
