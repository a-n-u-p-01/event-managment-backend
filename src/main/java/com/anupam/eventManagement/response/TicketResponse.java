package com.anupam.eventManagement.response;

import com.anupam.eventManagement.request.TicketDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponse {
    private int statusCode;
    private String message;
    private TicketDTO ticketDTO;
}
