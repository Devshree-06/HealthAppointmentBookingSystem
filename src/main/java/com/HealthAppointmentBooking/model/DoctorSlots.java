package com.HealthAppointmentBooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Table("doctor_slots")
public class DoctorSlots {
    @Id
    @Column("slot_id")
    private Integer slot_id;

    @Column("doctor_id")
    private Integer doctor_id;

    @Column("slot_time")
    private LocalTime slot_time;

    @Column("slot_date")
    private LocalDate slot_date;

    @Column("is_booked")
    private Boolean is_booked;
}
