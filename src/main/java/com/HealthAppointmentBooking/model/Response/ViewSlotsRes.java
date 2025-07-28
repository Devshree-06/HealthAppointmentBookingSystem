package com.HealthAppointmentBooking.model.Response;

import com.HealthAppointmentBooking.dto.Slots;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewSlotsRes {
    private String status;
    private int statusCode;
    private String message;
    private List<Slots> slots;
}
