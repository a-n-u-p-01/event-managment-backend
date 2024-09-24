package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.entity.Event;
import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.request.EventDTO;
import com.anupam.eventManagement.response.EventResponse;
import com.anupam.eventManagement.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping( "/create-event")
    public ResponseEntity createEvent(@RequestBody EventDTO event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        EventResponse newEvent = eventService.createEvent(event, Long.valueOf(currentUser.getId()));
        return  ResponseEntity.status(newEvent.getStatusCode()).body(newEvent);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Event>> getAllEvents(HttpServletResponse response){
        List<Event> eventList = eventService.findAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(eventList);
    }

    @GetMapping( "/get-events")
    public ResponseEntity<EventResponse> getAllEvents(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        EventResponse response = eventService.getAllUserEvents(currentUser.getId());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse event = eventService.findEventById(id);
        return ResponseEntity.status(event.getStatusCode()).body(event);
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId, @RequestBody EventDTO eventDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        EventResponse updatedEventResponse = eventService.updateById(Long.valueOf( currentUser.getId()), eventId, eventDetails);
        return ResponseEntity.status(updatedEventResponse.getStatusCode()).body(updatedEventResponse);
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity DeleteEvent( @PathVariable Long eventId) {
        try {
            eventService.deleteByEventId(eventId);
            return new ResponseEntity<>("deleted" ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/change-status/{eventId}")
    public ResponseEntity<String> changeStatus(@PathVariable Long eventId) {
        try {
            boolean statusChanged = eventService.changeStatus(eventId);
            if (statusChanged) {
                return new ResponseEntity<>("Status changed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Event present", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
