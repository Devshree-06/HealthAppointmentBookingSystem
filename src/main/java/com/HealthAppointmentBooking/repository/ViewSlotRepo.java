package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.dto.Slots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class ViewSlotRepo {

    @Autowired
    R2dbcEntityTemplate template;

    public Flux<Slots> getAvailableSlots(String category,String name){
        String query = "select ds.slot_time,ds.slot_date,dm.category,dm.username \n" +
                "from doctor_slots ds\n" +
                "join doctor_master dm on ds.doctor_id = dm.doctor_id\n" +
                "where dm.category = :category and dm.username = :name\n" +
                "and ds.is_booked = false;";

        return template.getDatabaseClient().sql(query)
                .bind("category",category)
                .bind("name",name)
                .map((row, rowMetadata) -> Slots.builder()
                        .slotDate(row.get("slot_date", LocalDate.class))
                        .slotTime(row.get("slot_time", LocalTime.class))
                        .build())
                .all();
    }
}
