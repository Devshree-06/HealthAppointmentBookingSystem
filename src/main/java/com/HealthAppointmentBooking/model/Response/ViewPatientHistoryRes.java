package com.HealthAppointmentBooking.model.Response;

import com.HealthAppointmentBooking.dto.ViewPatientHistoryData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewPatientHistoryRes {

    private String status;
    private int statusCode;
    private String message;
    private List<ViewPatientHistoryData> data;
}
