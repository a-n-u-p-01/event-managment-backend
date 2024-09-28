package com.anupam.eventManagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long ticketId;
    private Integer ticketType;
    private Double price;
    private EventDTO event;
    private UserDTO attendee;
    private PaymentDTO payment;
    private Boolean cancelStatus;

}
