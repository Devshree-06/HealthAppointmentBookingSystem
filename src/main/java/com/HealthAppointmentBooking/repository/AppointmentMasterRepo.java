package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.AppointmentMaster;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AppointmentMasterRepo extends ReactiveCrudRepository<AppointmentMaster,String> {

    @Query("UPDATE appointment_master " +
            "SET booking_status = :status, reason = :reason " +
            "WHERE appointment_id = :appointmentId")
    Mono<Integer> updateAppointmentStatus(String status,String reason,String appointmentId);
}
