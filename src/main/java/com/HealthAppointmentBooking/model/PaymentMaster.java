package com.HealthAppointmentBooking.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("payment_master")
@Builder
public class PaymentMaster {
    @Id
    @Column("payment_id")
    private Integer paymentId;

    @Column("appointment_id")
    private String appointmentId;

    @Column("razorpay_order_id")
    private String razorpayOrderId;

    @Column("amount")
    private Double amount;

    @Column("payment_status")
    private String paymentStatus;

    @Column("created_at")
    private LocalDateTime createdAt;
}
