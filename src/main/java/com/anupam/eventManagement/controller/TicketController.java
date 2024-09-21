package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.model.User;
import com.anupam.eventManagement.request.AttendeeDTO;
import com.anupam.eventManagement.request.TicketDTO;
import com.anupam.eventManagement.request.TicketSalesDTO;
import com.anupam.eventManagement.response.EventResponse;
import com.anupam.eventManagement.response.TicketResponse;
import com.anupam.eventManagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/register/{eventId}")
    public ResponseEntity<TicketResponse> registerAttendee(@PathVariable Long eventId,
                                                   @RequestBody TicketDTO ticketDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        TicketResponse ticketResponse=ticketService.registerAttendee(eventId,Long.valueOf( currentUser.getId()), ticketDTO);

        return  ResponseEntity.status(ticketResponse.getStatusCode()).body(ticketResponse);

    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<List<AttendeeDTO>> getAttendeeList(@PathVariable Long eventId) {
        List<AttendeeDTO> attendees = ticketService.getAttendeeList(eventId);

        return ResponseEntity.ok(attendees);
    }

    @GetMapping("/ticket-sales/{eventId}")
    public ResponseEntity<TicketSalesDTO> getTicketSales(@PathVariable Long eventId) {
        TicketSalesDTO ticketSalesDTO = ticketService.getTicketSales(eventId);
        return ResponseEntity.ok(ticketSalesDTO);
    }

    @GetMapping("/attendee-events")
    public ResponseEntity<EventResponse> getEventsByUserAsAttendee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        EventResponse eventResponse = ticketService.getEventsByUserAsAttendee(Long.valueOf((currentUser.getId())));
        return ResponseEntity.status(eventResponse.getStatusCode()).body(eventResponse);
    }

}
