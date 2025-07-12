package com.HealthAppointmentBooking.model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRes {

    private String status;
    private int statusCode;
    private String message;
}
