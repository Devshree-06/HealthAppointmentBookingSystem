package com.HealthAppointmentBooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("doctor_master")
public class DoctorMaster {
    @Id
    @Column("doctor_id")
    private Integer doctor_id;
    @Column("username")
    public String username;
}
