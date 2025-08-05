package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.Utils.CommonMethods;
import com.HealthAppointmentBooking.dto.PaymentDO;
import com.HealthAppointmentBooking.model.PaymentMaster;
import com.HealthAppointmentBooking.model.Request.BookAppointmentReq;
import com.HealthAppointmentBooking.model.Response.BookAppointmentRes;
import com.HealthAppointmentBooking.repository.BookAppointmentRepo;
import com.HealthAppointmentBooking.repository.DoctorSlotRepo;
import com.HealthAppointmentBooking.repository.RazorPayRepo;
import com.HealthAppointmentBooking.repository.ViewSlotRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BookAppointmentService {

    @Autowired
        ViewSlotRepo viewSlotRepo;
    @Autowired
    BookAppointmentRepo bookAppointmentRepo;
    @Autowired
    DoctorSlotRepo doctorSlotRepo;
    @Autowired
    RazorpayPaymentService razorpayPaymentService;
    @Autowired
    RazorPayRepo razorPayRepo;


        public Mono<ResponseEntity<BookAppointmentRes>> bookAppointment(BookAppointmentReq bookAppointmentReq){

            return viewSlotRepo.getAvailableSlots(bookAppointmentReq.getCategory(),bookAppointmentReq.getDoctorName())
                    .collectList()
                    .flatMap(appointment->{
                        if(appointment == null || appointment.isEmpty()){
                            return Mono.just(ResponseEntity.ok(new BookAppointmentRes("Fail",100,"Cannot book appointment.No slots available")));
                        }

                        PaymentDO paymentDO = new PaymentDO();
                        paymentDO.setAppointmentId(UUID.randomUUID().toString());
                        paymentDO.setAmount(bookAppointmentReq.getAmount());

                        return razorpayPaymentService.createOrder(paymentDO)
                                .flatMap(paymentResult->{

                                    if(paymentResult == null){
                                        return Mono.just(ResponseEntity.ok(new BookAppointmentRes("fail",100,"Payment not successfull.")));
                                    }

                                    String razorPayId = paymentResult.getString("id");
                                    PaymentMaster paymentMaster = PaymentMaster.builder()
                                            .razorpayOrderId(razorPayId)
                                            .appointmentId(paymentDO.getAppointmentId())
                                            .amount(paymentDO.getAmount())
                                            .paymentStatus("Payment Done")
                                            .createdAt(LocalDateTime.now())
                                            .build();

                                    return razorPayRepo.save(paymentMaster)
                                            .then(bookAppointmentRepo.updateAppointment(bookAppointmentReq,paymentDO))
                                            .then(doctorSlotRepo.updateBookingStatus(CommonMethods.parseToLocalTime(bookAppointmentReq.getSlotTime()),CommonMethods.parseToLocalDate(bookAppointmentReq.getSlotDate())))
                                            .thenReturn(ResponseEntity.ok(new BookAppointmentRes("Success",200,"Appointment booked successfully.")));
                                })
                                .onErrorResume(error->{
                                    log.info("The error occurred is : {}",error.getMessage());
                                    return Mono.just(ResponseEntity.ok(
                                            new BookAppointmentRes("Fail", 100, "Internal server error occurred.")));
                                });

                    });

        }

}
