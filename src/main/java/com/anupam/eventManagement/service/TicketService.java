package com.anupam.eventManagement.service;

import com.anupam.eventManagement.model.Ticket;
import com.anupam.eventManagement.request.AttendeeDTO;
import com.anupam.eventManagement.request.TicketDTO;
import com.anupam.eventManagement.request.TicketSalesDTO;
import com.anupam.eventManagement.response.EventResponse;
import com.anupam.eventManagement.response.TicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse registerAttendee(Long eventId, Long userId, TicketDTO ticketDTO);

    List<AttendeeDTO> getAttendeeList(Long eventId);

    TicketSalesDTO getTicketSales(Long eventId);

    EventResponse getEventsByUserAsAttendee(Long userId);

    List<TicketDTO> getBookedTickets(Integer id);

    Integer getBookedNumber(Long eventId);
}
