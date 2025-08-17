package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.dto.RefundDetailsData;
import com.HealthAppointmentBooking.model.PaymentMaster;
import com.HealthAppointmentBooking.model.Request.CancelAppointmentReq;
import com.HealthAppointmentBooking.model.Response.CancelAppointmentRes;
import com.HealthAppointmentBooking.repository.AppointmentMasterRepo;
import com.HealthAppointmentBooking.repository.BookAppointmentRepo;
import com.HealthAppointmentBooking.repository.PaymentMasterRepo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class CancelAppointmentService {

    @Autowired
    AppointmentMasterRepo appointmentMasterRepo;
    @Autowired
    PaymentMasterRepo paymentMasterRepo;
    @Autowired
    RazorpayPaymentService razorpayPaymentService;

    public Mono<ResponseEntity<CancelAppointmentRes>> cancelAppointment(CancelAppointmentReq request){

        return appointmentMasterRepo.findById(request.getAppointmentId())
                .flatMap(result-> paymentMasterRepo.findByAppointmentId(request.getAppointmentId())
                        .flatMap(payment->{

                            log.info("The payment response from razorpay is : {}",payment);
                            if("Payment Done".equalsIgnoreCase(payment.getPaymentStatus())){

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime bookingSlot = LocalDateTime.parse(result.getBookingSlot(), formatter);

                                LocalDateTime timeNow  = LocalDateTime.now();

                                long timeDiff = ChronoUnit.HOURS.between(timeNow,bookingSlot);

                                int refundPercent;

                                if(timeDiff >= 24){
                                    refundPercent = 100;
                                }
                                else if(timeDiff > 0){
                                    refundPercent = 50;
                                }
                                else{
                                    refundPercent = 0;
                                }

                                int refundAmt = (int)((payment.getAmount() * refundPercent) / 100);

                                if(refundAmt>0){
                                    return razorpayPaymentService.refundPayment(
                                                    String.valueOf(payment.getPaymentId()),
                                                    refundAmt,
                                                    request.getReason()
                                            )
                                            .flatMap(paymentResult -> {
                                                String refundId = (String) paymentResult.get("id");
                                                String refundStatus = (String) paymentResult.get("status");

                                                return appointmentMasterRepo.updateAppointmentStatus("CANCELLED",request.getReason(), request.getAppointmentId())
                                                        .then(paymentMasterRepo.updateRefundDetails(
                                                                payment.getPaymentId(),
                                                                refundAmt,
                                                                refundPercent,
                                                                refundId,
                                                                refundStatus
                                                        ))
                                                        .thenReturn(ResponseEntity.ok(
                                                                CancelAppointmentRes.builder()
                                                                        .status("Success")
                                                                        .statusCode(200)
                                                                        .message("Appointment cancelled successfully.")
                                                                        .data(
                                                                                RefundDetailsData.builder()
                                                                                        .refundPercent(refundPercent)
                                                                                        .refundAmount(refundAmt)
                                                                                        .refundStatus(refundStatus)
                                                                                        .refundId(refundId)
                                                                                        .build()
                                                                        )
                                                                        .build()
                                                        ));
                                            });
                                }
                                else{
                                    return appointmentMasterRepo.updateAppointmentStatus("CANCELLED",request.getReason(), request.getAppointmentId())
                                            .thenReturn(ResponseEntity.ok(CancelAppointmentRes.builder()
                                                    .status("SUCCESS")
                                                    .statusCode(200)
                                                    .message("Appointment cancelled without refund.").build()));
                                }
                            }
                            else{
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(CancelAppointmentRes.builder()
                                                .status("FAILED")
                                                .statusCode(400)
                                                .message("Payment not successful, cannot cancel appointment")
                                                .build()));
                            }
                        })
                        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(CancelAppointmentRes.builder()
                                        .status("Failed")
                                        .statusCode(404)
                                        .message("Payment not found. Please try again.")
                                        .build()))))

                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CancelAppointmentRes.builder()
                                .status("Failed")
                                .statusCode(404)
                                .message("Appointment not found.Please check again")
                                .build())));

    }
}
