package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.model.Request.RegisterUserReq;
import com.HealthAppointmentBooking.model.Response.RegisterUserRes;
import com.HealthAppointmentBooking.model.UserMaster;
import com.HealthAppointmentBooking.repository.RegisterUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RegisterUserService {

    @Autowired
    RegisterUserRepo registerUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Mono<ResponseEntity<RegisterUserRes>> registerNewUser(RegisterUserReq request) {

        return registerUserRepo.findByUsername(request.getUserName())
                .flatMap(existingUser -> {
                    log.info("User details found");
                    return Mono.just(ResponseEntity.ok(
                            new RegisterUserRes("Fail", 100, "User already exists")));
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            log.info("User not found, creating new user");

                            UserMaster newUser = new UserMaster();
                            newUser.setUsername(request.getUserName());
                            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                            newUser.setRolename(request.getRoleName());

                            return registerUserRepo.save(newUser)
                                    .map(savedUser -> ResponseEntity.ok(
                                            new RegisterUserRes("Success", 200, "User is registered successfully")));
                        })
                );
    }

}
