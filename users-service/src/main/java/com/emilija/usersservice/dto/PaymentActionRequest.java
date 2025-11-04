package com.emilija.usersservice.dto;

import lombok.Data;

@Data
public class PaymentActionRequest {
    private Double amount;
    private String clientPaymentId;
}
