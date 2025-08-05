package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

@Data
public class BookAppointmentReq {
    private String patientName;
    private String doctorName;
    private String category;
    private String slotDate;
    private String slotTime;
    private Double amount;
    private String appointmentId;
}
