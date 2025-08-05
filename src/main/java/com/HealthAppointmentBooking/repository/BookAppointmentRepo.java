package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.dto.PaymentDO;
import com.HealthAppointmentBooking.model.AppointmentMaster;
import com.HealthAppointmentBooking.model.Request.BookAppointmentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@Slf4j
public class BookAppointmentRepo {

    @Autowired
    R2dbcEntityTemplate template;

    public Mono<AppointmentMaster> updateAppointment(BookAppointmentReq request, PaymentDO paymentDO){

        String query = "INSERT INTO appointment_master \n" +
                "(username, created_at, doctor_name, doctor_category, booking_slot, booking_status, amount) \n" +
                "VALUES (:username, :createdAt, :doctorName, :doctorCategory, :bookingSlot, :bookingStatus, :amount)\n";

        String bookingSlot = request.getSlotDate() +" " + request.getSlotTime();

        return template.getDatabaseClient().sql(query)
                .bind("username",request.getPatientName())
                .bind("createdAt", LocalDateTime.now())
                .bind("doctorName",request.getDoctorName())
                .bind("doctorCategory",request.getCategory())
                .bind("bookingSlot",bookingSlot)
                .bind("bookingStatus","Booked")
                .bind("amount",request.getAmount())
                .map((row, metadata) -> {
            AppointmentMaster appointment = new AppointmentMaster();
            appointment.setAppointmentId(row.get("appointment_id", Integer.class));
            appointment.setUsername(row.get("username", String.class));
            appointment.setCreatedAt(row.get("created_at", LocalDateTime.class));
            appointment.setDoctorName(row.get("doctor_name", String.class));
            appointment.setDoctorCategory(row.get("doctor_category", String.class));
            appointment.setBookingSlot(row.get("booking_slot", String.class));
            appointment.setBookingStatus(row.get("booking_status", String.class));
            appointment.setAmount(row.get("amount", Double.class));
            return appointment;
        })
                .one();
    }
}
