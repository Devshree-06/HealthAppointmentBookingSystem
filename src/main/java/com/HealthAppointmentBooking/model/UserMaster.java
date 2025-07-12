package com.HealthAppointmentBooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_master")
public class UserMaster {

    @Id
    @Column("id")
    private Integer id;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("rolename")
    private String rolename;

}
