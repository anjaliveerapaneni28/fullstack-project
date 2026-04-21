package com.smartcampus.repository;

import com.smartcampus.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDepartmentContainingIgnoreCaseOrEventTypeContainingIgnoreCase(String department, String eventType);
    List<Event> findByDate(LocalDate date);
}
