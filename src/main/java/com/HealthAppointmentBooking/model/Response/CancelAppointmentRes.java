package com.HealthAppointmentBooking.model.Response;

import com.HealthAppointmentBooking.dto.RefundDetailsData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelAppointmentRes {
    private String status;
    private int statusCode;
    private String message;
    private RefundDetailsData data;
}
