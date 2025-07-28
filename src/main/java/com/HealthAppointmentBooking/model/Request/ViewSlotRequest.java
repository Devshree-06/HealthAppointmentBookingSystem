package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

@Data
public class ViewSlotRequest {
    private String category;
    private String doctorName;
}
