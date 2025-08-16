package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.dto.PaymentDO;
import com.HealthAppointmentBooking.dto.ViewPatientHistoryData;
import com.HealthAppointmentBooking.model.AppointmentMaster;
import com.HealthAppointmentBooking.model.Request.BookAppointmentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@Slf4j
public class BookAppointmentRepo {

    @Autowired
    R2dbcEntityTemplate template;

    public Mono<AppointmentMaster> updateAppointment(BookAppointmentReq request, String appointmentId) {
        String query = "INSERT INTO appointment_master " +
                "(appointment_id, username, created_at, doctor_name, doctor_category, booking_slot, booking_status, amount) " +
                "VALUES (:appointmentId, :username, :createdAt, :doctorName, :doctorCategory, :bookingSlot, :bookingStatus, :amount) " +
                "RETURNING *";

        String bookingSlot = request.getSlotDate() + " " + request.getSlotTime();

        return template.getDatabaseClient().sql(query)
                .bind("appointmentId", appointmentId)
                .bind("username", request.getPatientName())
                .bind("createdAt", LocalDateTime.now())
                .bind("doctorName", request.getDoctorName())
                .bind("doctorCategory", request.getCategory())
                .bind("bookingSlot", bookingSlot)
                .bind("bookingStatus", "Booked")
                .bind("amount", request.getAmount())
                .map((row, metadata) -> AppointmentMaster.builder()
                        .appointmentId(row.get("appointment_id", String.class))
                        .username(row.get("username", String.class))
                        .createdAt(row.get("created_at", LocalDateTime.class))
                        .doctorName(row.get("doctor_name", String.class))
                        .doctorCategory(row.get("doctor_category", String.class))
                        .bookingSlot(row.get("booking_slot", String.class))
                        .bookingStatus(row.get("booking_status", String.class))
                        .amount(row.get("amount", Double.class))
                        .build()
                )
                .one();
    }


    public Flux<ViewPatientHistoryData> viewPatientHistory(String patientName){
        String query = "select am.appointment_id,am.username as patientName, am.doctor_name, am.booking_slot,am.booking_status,pm.payment_status from appointment_master\n" +
                "am join payment_master pm\n" +
                "on am.appointment_id = pm.appointment_id\n" +
                "where am.username = :patientName\n" +
                "and am.booking_slot >= NOW() - INTERVAL '7 days'";

        return template.getDatabaseClient().sql(query)
                .bind("patientName",patientName)
                .map((row, rowMetadata) -> {
                    LocalDateTime bookingSlot = row.get("booking_slot", LocalDateTime.class);
                    return ViewPatientHistoryData.builder()
                            .appointmentId(row.get("appointment_id", String.class))
                            .patientName(row.get("patientName", String.class))
                            .doctorName(row.get("doctor_name", String.class))
                            .slotDate(bookingSlot.toLocalDate().toString())
                            .slotTime(bookingSlot.toLocalTime().toString())
                            .bookingStatus(row.get("booking_status", String.class))
                            .paymentStatus(row.get("payment_status", String.class))
                            .build();
                })
                .all();

    }
}
