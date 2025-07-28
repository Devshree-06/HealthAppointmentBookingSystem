package com.HealthAppointmentBooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("appointment_master")
public class AppointmentMaster {

    @Id
    @Column("appointment_id")
    public int appointment_id;
    @Column("username")
    public String username;
    @Column("created_at")
    public LocalDateTime created_at;
    @Column("doctor_name")
    public String doctor_name;
    @Column("doctor_category")
    public String doctor_category;
    @Column("booking_slot")
    public String booking_slot;
    @Column("booking_status")
    public String booking_status;
    @Column("amount")
    public int amount;
}
