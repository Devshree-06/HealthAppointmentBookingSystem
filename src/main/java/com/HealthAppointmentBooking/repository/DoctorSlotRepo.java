package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.DoctorSlots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Repository
public class DoctorSlotRepo {

    @Autowired
    R2dbcEntityTemplate template;

    public Mono<Long> updateBookingStatus(LocalTime slotTime, LocalDate slotDate){
        String query = "UPDATE doctor_slots set is_booked = true where slot_time = :slotTime and slot_date = :slotDate";

        return template.getDatabaseClient().sql(query)
                .bind("slotTime",slotTime)
                .bind("slotDate",slotDate)
                .fetch()
                .rowsUpdated();

    }


}
