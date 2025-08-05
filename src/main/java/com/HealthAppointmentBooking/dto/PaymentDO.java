package com.HealthAppointmentBooking.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data

public class PaymentDO {

    private Double amount;
    private String appointmentId;
}
