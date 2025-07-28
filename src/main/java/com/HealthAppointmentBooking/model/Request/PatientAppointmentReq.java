package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

import java.util.List;

@Data
public class PatientAppointmentReq {
    public String username;
    public List<String> doctor_category;
}
