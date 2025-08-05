package com.HealthAppointmentBooking.model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookAppointmentRes {
    private String status;
    private int statusCode;
    private String message;

}
