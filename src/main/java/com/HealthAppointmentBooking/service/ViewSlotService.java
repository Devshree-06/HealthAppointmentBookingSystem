package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.model.Request.ViewSlotRequest;
import com.HealthAppointmentBooking.model.Response.ViewSlotsRes;
import com.HealthAppointmentBooking.repository.ViewSlotsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ViewSlotService {

    @Autowired
    ViewSlotsRepo viewSlotsRepo;

    public Mono<ResponseEntity<ViewSlotsRes>> viewSlotsForBooking(ViewSlotRequest viewSlotRequest){

        return viewSlotsRepo.getAvailableSlots(viewSlotRequest.getCategory(),viewSlotRequest.getDoctorName())
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
