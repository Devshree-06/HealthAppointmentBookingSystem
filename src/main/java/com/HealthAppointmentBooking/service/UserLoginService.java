package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.Utils.Jwt;
import com.HealthAppointmentBooking.model.Request.LoginUserReq;
import com.HealthAppointmentBooking.model.Response.LoginUserRes;
import com.HealthAppointmentBooking.repository.LoginUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserLoginService {

    @Autowired
    LoginUserRepo loginUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Jwt jwtUtil;

    public Mono<ResponseEntity<LoginUserRes>> loginUser(LoginUserReq request){

        return loginUserRepo.findByUsernameAndRolename(request.getUsername(), request.getRolename())
                .flatMap(loginUser->{
                    log.info("the login user details is : "+loginUser);
                    if(passwordEncoder.matches(request.getPassword(), loginUser.getPassword())){
                        log.info("The user password is : "+loginUser.getPassword());
                        log.info("The request password is : "+request.getPassword());
                        String token = jwtUtil.generateToken(request.getUsername(), request.getRolename());
                        log.info("The user exists and is validated");
                        return Mono.just(ResponseEntity.ok(new LoginUserRes("Success",200,"Login Successfuly",token)));
                    }
                    else{
                        return Mono.just(ResponseEntity.ok(new LoginUserRes("fail",100,"Invalid Credentials",null)));
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("User not found for username: {}, roleName: {}", request.getUsername(), request.getRolename());
                    return Mono.just(ResponseEntity.ok(
                            new LoginUserRes("Fail", 100, "User not found", null)));
                }));
    }

}
