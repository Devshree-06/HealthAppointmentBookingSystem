package com.HealthAppointmentBooking.controller;

import com.HealthAppointmentBooking.model.Request.BookAppointmentReq;
import com.HealthAppointmentBooking.model.Request.LoginUserReq;
import com.HealthAppointmentBooking.model.Request.RegisterUserReq;
import com.HealthAppointmentBooking.model.Request.ViewSlotReq;
import com.HealthAppointmentBooking.model.Response.BookAppointmentRes;
import com.HealthAppointmentBooking.model.Response.LoginUserRes;
import com.HealthAppointmentBooking.model.Response.RegisterUserRes;
import com.HealthAppointmentBooking.model.Response.ViewSlotsRes;
import com.HealthAppointmentBooking.service.BookAppointmentService;
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
    @Autowired
    ViewSlotService viewSlotService;
    @Autowired
    BookAppointmentService bookAppointmentService;

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

    @PostMapping("/viewSlots")
    public Mono<ResponseEntity<ViewSlotsRes>> viewSlot(@RequestBody ViewSlotReq req){
        String reqId = String.valueOf(UUID.randomUUID());
        return viewSlotService.viewSlotsForBooking(req)
                .doOnNext(result-> log.info("View slots is successful"));
    }

    @PostMapping("/bookAppointment")
    public Mono<ResponseEntity<BookAppointmentRes>> bookAppointment(@RequestBody BookAppointmentReq req){
        String reqId = UUID.randomUUID().toString();
        return bookAppointmentService.bookAppointment(req)
                .doOnNext(result-> log.info("Appointment is booked successfully" + reqId));
    }
}
