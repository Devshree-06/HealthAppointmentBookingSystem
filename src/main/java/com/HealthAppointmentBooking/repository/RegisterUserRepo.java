package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.UserMaster;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RegisterUserRepo extends ReactiveCrudRepository<UserMaster,Long> {

    Mono<UserMaster> findByUsername(String userName);
}
