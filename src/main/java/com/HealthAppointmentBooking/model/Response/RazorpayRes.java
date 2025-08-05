package com.HealthAppointmentBooking.model.Response;

import lombok.Data;

@Data
public class RazorpayRes {
    private String status;
    private int statusCode;
    private String message;
}
