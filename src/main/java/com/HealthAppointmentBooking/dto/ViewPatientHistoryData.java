package com.HealthAppointmentBooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewPatientHistoryData {
    private String appointmentId;
    private String patientName;
    private String doctorName;
    private String slotDate;
    private String slotTime;
    private String bookingStatus;
    private String paymentStatus;
}
