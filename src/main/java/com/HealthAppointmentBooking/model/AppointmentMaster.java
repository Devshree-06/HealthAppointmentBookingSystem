package com.HealthAppointmentBooking.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("appointment_master")
@Builder
public class AppointmentMaster {

    @Id
    @Column("appointment_id")
    private String appointmentId;

    @Column("username")
    private String username;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("doctor_name")
    private String doctorName;

    @Column("doctor_category")
    private String doctorCategory;

    @Column("booking_slot")
    private String bookingSlot;

    @Column("booking_status")
    private String bookingStatus;

    @Column("amount")
    private Double amount;
}
