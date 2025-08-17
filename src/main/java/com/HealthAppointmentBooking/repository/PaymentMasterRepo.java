package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.PaymentMaster;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentMasterRepo extends ReactiveCrudRepository<PaymentMaster,String> {

    Mono<PaymentMaster> findByAppointmentId(String appointmentId);

    @Query("UPDATE payment_master " +
            "SET refund_amount = :refundAmount, " +
            "refund_percent = :refundPercent, " +
            "refund_id = :refundId, " +
            "refund_status = :refundStatus " +
            "WHERE payment_id = :paymentId")
    Mono<Integer> updateRefundDetails(int paymentId,int refundAmount,int refundPercent,String refundId,String refundStatus);
}
