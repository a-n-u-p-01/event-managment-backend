package com.anupam.eventManagement.repository;

import com.anupam.eventManagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event , Long> {
    public List<Event> findByEventIdAndOrganizer_id(Long eventId, Long Organizer_id);

    Long findByOrganizerId(Long userId);
    List<Event> findAllByOrganizerId(Long userId);
}
