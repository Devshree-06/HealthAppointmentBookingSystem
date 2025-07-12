package com.HealthAppointmentBooking.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RegisterUserReq {
    private String userName;
    private String password;
    private String roleName;

}
