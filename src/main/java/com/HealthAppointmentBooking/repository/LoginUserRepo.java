package com.HealthAppointmentBooking.repository;

import com.HealthAppointmentBooking.model.UserMaster;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LoginUserRepo extends ReactiveCrudRepository<UserMaster,Long> {

    @Query("select * from user_master where username = $1 AND rolename = $2")
    Mono<UserMaster> findByUsernameAndRolename(String username, String rolename);
}
