package com.HealthAppointmentBooking.service;

import com.HealthAppointmentBooking.dto.PaymentDO;
import com.HealthAppointmentBooking.model.Response.RazorpayRes;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class RazorpayPaymentService {

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.secretKey}")
    private String secretKey;

    @Value("${razorpay.url}")
    private String url;

    @Autowired
    private WebClient webClient;

    public Mono<JSONObject> createOrder(PaymentDO paymentDO){

        return Mono.fromCallable(() ->{

            RazorpayClient razorpayClient = new RazorpayClient(keyId,secretKey);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (paymentDO.getAmount() * 100));
            orderRequest.put("currency","INR");
            orderRequest.put("receipt",paymentDO.getAppointmentId());

            Order order = razorpayClient.orders.create(orderRequest);
            return order.toJson();
        });
    }

    public Mono<Map> refundPayment(String paymentId,int amount,String reason){

        return webClient.post()
                .uri(url + "/payments/{paymentId}/refund",paymentId)
                .bodyValue(Map.of(
                        "amount",amount,
                        "speed","optimum",
                        "notes",Map.of("reason",reason)
                ))
                .retrieve()
                .bodyToMono(Map.class);
    }
}
