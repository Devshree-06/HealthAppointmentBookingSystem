package com.HealthAppointmentBooking.model.Request;

import lombok.Data;

@Data
public class LoginUserReq {
    private String username;
    private String rolename;
    private String password;
}
