package com.anupam.eventManagement.request;

import lombok.Data;

@Data
public class TicketPricingDTO {
    private Double basicPrice;
    private Double standardPrice;
    private Double premiumPrice;

}
