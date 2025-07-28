package com.HealthAppointmentBooking.controller;

import com.HealthAppointmentBooking.model.Request.LoginUserReq;
import com.HealthAppointmentBooking.model.Request.PatientDashboardReq;
import com.HealthAppointmentBooking.model.Request.RegisterUserReq;
import com.HealthAppointmentBooking.model.Request.ViewSlotRequest;
import com.HealthAppointmentBooking.model.Response.LoginUserRes;
import com.HealthAppointmentBooking.model.Response.PatientDashboardRes;
import com.HealthAppointmentBooking.model.Response.RegisterUserRes;
import com.HealthAppointmentBooking.model.Response.ViewSlotsRes;
//import com.HealthAppointmentBooking.service.PatientDashboardService;
import com.HealthAppointmentBooking.service.RegisterUserService;
import com.HealthAppointmentBooking.service.UserLoginService;
import com.HealthAppointmentBooking.service.ViewSlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthAppointmentController {

    @Autowired
    RegisterUserService registerUserService;
    @Autowired
    UserLoginService userLoginService;
//    @Autowired
//    PatientDashboardService patientDashboardService;
    @Autowired
    ViewSlotService viewSlotService;

    @PostMapping("/register")
    public Mono<ResponseEntity<RegisterUserRes>> register(@RequestBody RegisterUserReq request){

        String reqId = String.valueOf(UUID.randomUUID());
        return registerUserService.registerNewUser(request)
                .doOnNext(result->log.info("New User is created "+reqId));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginUserRes>> loginUser(@RequestBody LoginUserReq request){
        String reqId = String.valueOf(UUID.randomUUID());
        return userLoginService.loginUser(request)
                .doOnNext(result->log.info("Login is successfull" + reqId));
    }

//    @PostMapping("/patientDashboard")
//    public Mono<ResponseEntity<PatientDashboardRes>> patientDashboard(@RequestBody PatientDashboardReq request){
//        String reqId = String.valueOf(UUID.randomUUID());
//        return patientDashboardService.getPatientDashboard(request)
//                .doOnNext(result-> log.info("Patient dashboard is fetched" + reqId));
//    }

    @PostMapping("/viewSlots")
    public Mono<ResponseEntity<ViewSlotsRes>> viewSlots(@RequestBody ViewSlotRequest request){
        String reqId = UUID.randomUUID().toString();
        return viewSlotService.viewSlotsForBooking(request)
                .doOnNext(result-> log.info("View slot is successful" + reqId));
    }
}
