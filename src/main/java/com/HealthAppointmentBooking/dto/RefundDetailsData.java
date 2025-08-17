package com.HealthAppointmentBooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundDetailsData {
    private int refundPercent;
    private int refundAmount;
    private String refundStatus;
    private String refundId;
}
