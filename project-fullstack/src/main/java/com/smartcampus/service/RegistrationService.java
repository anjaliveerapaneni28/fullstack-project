package com.smartcampus.service;

import com.smartcampus.model.Event;
import com.smartcampus.model.Registration;
import com.smartcampus.model.User;
import com.smartcampus.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public boolean registerUserToEvent(Registration registration) {
        Optional<Registration> existing = registrationRepository.findByUserAndEvent(registration.getUser(), registration.getEvent());
        if (existing.isPresent()) {
            return false; // Already registered
        }
        registrationRepository.save(registration);
        return true;
    }

    public List<Registration> getUserRegistrations(User user) {
        return registrationRepository.findByUser(user);
    }

    public long getEventRegistrationCount(Event event) {
        return registrationRepository.countByEvent(event);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
}
