package com.anupam.eventManagement.service.impl;

import com.anupam.eventManagement.exception.EventException;
import com.anupam.eventManagement.exception.UserException;
import com.anupam.eventManagement.entity.Event;
import com.anupam.eventManagement.entity.TicketPricing;
import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.repository.EventRepository;
import com.anupam.eventManagement.repository.TicketPricingRepository;
import com.anupam.eventManagement.repository.TicketRepository;
import com.anupam.eventManagement.repository.UserRepository;
import com.anupam.eventManagement.request.EventDTO;
import com.anupam.eventManagement.response.EventResponse;
import com.anupam.eventManagement.service.EventService;
import com.anupam.eventManagement.utils.RandomImageGenerator;
import com.anupam.eventManagement.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketPricingRepository ticketPricingRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public EventResponse createEvent(EventDTO event, Long userId) {
        EventResponse response = new EventResponse();
        try {
            Optional<User> organizer = userRepository.findById(userId);
            if (organizer.isPresent()) {
                Event eventData = Utils.mapEventDTOToEvent(event);
                eventData.setOrganizer(organizer.get());
                eventData.setStatus(true);
                eventData.setImageUrl(RandomImageGenerator.getRandomImageUrl());
                eventData = eventRepository.save(eventData);
                EventDTO eventDTO = Utils.mapEventEntityToEventDTO(eventData);
                response.setEvent(eventDTO);
                response.setMessage("Event created successfully");
                response.setStatusCode(201);
            } else {
                throw new EventException("User not found");
            }
        } catch (EventException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Event creation failed: " + e.getMessage());
        }
        return response;
    }

    @Override
    public EventResponse getAllUserEvents(Long userId) {
        EventResponse response = new EventResponse();
        try {
            List<Event> events = eventRepository.findAllByOrganizerId(userId);
            response.setEvents(events);
            response.setStatusCode(200);
            response.setMessage("User data retrieved successfully");
        } catch (UserException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user details: " + e.getMessage());
        }
        return response;
    }

    @Override
    public boolean changeStatus(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setStatus(!event.getStatus()); // Toggle the status
            eventRepository.save(event);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public EventResponse findEventById(Long eventId) {
        EventResponse response = new EventResponse();
        try {
            Optional<Event> eventOptional = eventRepository.findById(eventId);
            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();
                EventDTO eventDTO = Utils.mapEventEntityToEventDTO(event);
                response.setEvent(eventDTO);
                response.setMessage("Event retrieved successfully");
                response.setStatusCode(200);
            } else {
                throw new EventException("Event not found with ID: " + eventId);
            }
        } catch (EventException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Server error: " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    @Transactional
    public void deleteByEventId(Long eventId) {
        try {
            // Deleting the event; associated tickets will be deleted automatically
            eventRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting event: " + e.getMessage());
        }
    }



    @Override
    public EventResponse updateById(Long userId, Long eventId, EventDTO eventDetails) {
        EventResponse response = new EventResponse();

        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent()) {
                throw new UserException("User not found with ID: " + userId);
            }

            Optional<Event> eventOptional = eventRepository.findById(eventId);
            if (!eventOptional.isPresent()) {
                throw new EventException("Event not found with ID: " + eventId);
            }

            Event event = eventOptional.get();
            event.setTitle(eventDetails.getTitle());
            event.setDescription(eventDetails.getDescription());
            event.setLocation(eventDetails.getLocation());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setCapacity(eventDetails.getCapacity());

            if (eventDetails.getTicketPricing() != null) {
                TicketPricing newPricing = Utils.mapTicketPricingDTOToTicketPricingEntity(eventDetails.getTicketPricing());
                event.setTicketPricing(ticketPricingRepository.save(newPricing));
            }

            event = eventRepository.save(event);
            response.setEvent(Utils.mapEventEntityToEventDTO(event));
            response.setMessage("Event updated successfully");
            response.setStatusCode(200);
        } catch (UserException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (EventException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Event update failed: " + e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }
}
