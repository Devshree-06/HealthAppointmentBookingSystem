package com.HealthAppointmentBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class HealthAppointmentBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthAppointmentBookingApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.build();
	}

}
