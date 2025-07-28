package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

@Data
public class ViewSlotReq {
    private String category;
    private String doctorName;
}
