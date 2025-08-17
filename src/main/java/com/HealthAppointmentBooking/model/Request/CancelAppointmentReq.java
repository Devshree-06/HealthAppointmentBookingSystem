package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

@Data
public class CancelAppointmentReq {
    private String appointmentId;
    private String reason;
}
