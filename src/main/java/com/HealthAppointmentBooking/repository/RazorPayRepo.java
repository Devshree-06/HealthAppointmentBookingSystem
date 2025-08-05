package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.PaymentMaster;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazorPayRepo extends ReactiveCrudRepository<PaymentMaster,String> {

}
