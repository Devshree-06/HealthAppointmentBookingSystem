package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.Utils.Jwt;
import com.HealthAppointmentBooking.model.Response.ViewPatientHistoryRes;
import com.HealthAppointmentBooking.repository.BookAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class ViewPatientHistoryService {

    @Autowired
    BookAppointmentRepo bookAppointmentRepo;
    @Autowired
    Jwt jwt;


    public Mono<ResponseEntity<ViewPatientHistoryRes>> viewPatientHistory(ServerWebExchange exchange){

        String patientName = jwt.getUserName(exchange);

        return bookAppointmentRepo.viewPatientHistory(patientName)
                .collectList()
                .map(result->{

                   ViewPatientHistoryRes res = new ViewPatientHistoryRes(
                           "Success",
                           200,
                           "History fetched Successfully",
                           result
                   );

                   return ResponseEntity.ok(res);

                })
                .switchIfEmpty(
                        Mono.just(ResponseEntity.ok(
                                new ViewPatientHistoryRes(
                                        "Fail",
                                        404,
                                        "No history found",
                                        null
                                )
                        ))
                );

    }
}
