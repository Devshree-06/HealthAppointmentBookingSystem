package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.model.Request.ViewSlotReq;
import com.HealthAppointmentBooking.model.Response.ViewSlotsRes;
import com.HealthAppointmentBooking.repository.ViewSlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ViewSlotService {

    @Autowired
    ViewSlotRepo viewSlotRepo;

    public Mono<ResponseEntity<ViewSlotsRes>> viewSlotsForBooking(ViewSlotReq viewSlotReq){

        return viewSlotRepo.getAvailableSlots(viewSlotReq.getCategory(),viewSlotReq.getDoctorName())
                .collectList()
                .flatMap(slots->{
                    if(slots == null || slots.isEmpty()){
                        return Mono.just(ResponseEntity.ok(new ViewSlotsRes("fail",100,"No slots are available",null)));

                    }

                    else{

                        return Mono.just(ResponseEntity.ok(new ViewSlotsRes("Success",200,"Slots are available",slots)));
                    }
                });
    }
}
