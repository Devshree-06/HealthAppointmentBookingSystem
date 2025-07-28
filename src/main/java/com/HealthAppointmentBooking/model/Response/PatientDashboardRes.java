package com.HealthAppointmentBooking.model.Response;

import com.HealthAppointmentBooking.DTO.PatientDashboardTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientDashboardRes {
    private String status;
    private int statusCode;
    private String message;
    private PatientDashboardTypes data;
}
