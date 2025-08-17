package com.HealthAppointmentBooking.controller;

import com.HealthAppointmentBooking.model.Request.*;
import com.HealthAppointmentBooking.model.Response.*;
import com.HealthAppointmentBooking.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
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
    @Autowired
    ViewPatientHistoryService viewPatientHistoryService;
    @Autowired
    CancelAppointmentService cancelAppointmentService;

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

    @PostMapping("/patient/history")
    public Mono<ResponseEntity<ViewPatientHistoryRes>> viewPatientHistory(ServerWebExchange exchange){
        String reqId = UUID.randomUUID().toString();
        return viewPatientHistoryService.viewPatientHistory(exchange)
                .doOnNext(result-> log.info("History is fetched successfully" + reqId));
    }

    @PostMapping("/cancelAppointment")
    public Mono<ResponseEntity<CancelAppointmentRes>> cancelAppointment(@RequestBody CancelAppointmentReq request){
        String reqId = UUID.randomUUID().toString();
        return cancelAppointmentService.cancelAppointment(request)
                .doOnNext(result-> log.info("Appointment cancelled successfully" + reqId));
    }
}
