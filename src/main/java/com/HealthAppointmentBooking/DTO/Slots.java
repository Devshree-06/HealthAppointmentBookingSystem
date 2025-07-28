package com.HealthAppointmentBooking.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class Slots {
    private LocalTime slotTime;
    private LocalDate slotDate;
}
