package com.anupam.eventManagement.response;

import com.anupam.eventManagement.request.PaymentDTO;
import lombok.Data;

@Data
public class PaymentResponse {
    private int statusCode;
    private String message;
    private PaymentDTO paymentDTO;
}
