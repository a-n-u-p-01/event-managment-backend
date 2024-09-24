package com.anupam.eventManagement.service;

import com.anupam.eventManagement.entity.Event;
import com.anupam.eventManagement.request.EventDTO;
import com.anupam.eventManagement.response.EventResponse;

import java.util.List;

public interface EventService {

    EventResponse createEvent(EventDTO event, Long userId);

//    List<Event> findEventsByOrganizer(Long userId);


    EventResponse findEventById(Long eventId);

    void deleteByEventId(Long eventId);

    EventResponse updateById(Long userId,Long eventId, EventDTO eventDetails);

    List<Event> findAllEvents();

    EventResponse getAllUserEvents(Long userId);

    boolean changeStatus(Long eventId);
}

