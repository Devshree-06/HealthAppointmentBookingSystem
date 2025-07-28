//package com.HealthAppointmentBooking.service;
//
//import com.HealthAppointmentBooking.DTO.PatientDashboardTypes;
//import com.HealthAppointmentBooking.model.Request.PatientDashboardReq;
//import com.HealthAppointmentBooking.model.Response.PatientDashboardRes;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class PatientDashboardService {
//
//    public Mono<ResponseEntity<PatientDashboardRes>> getPatientDashboard(PatientDashboardReq request){
//
//
//        return Mono.just(ResponseEntity.ok(new PatientDashboardRes("Success",200,"Dashboard details fetched successfully", (PatientDashboardTypes) types)));
//    }
//}
