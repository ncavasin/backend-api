package com.sip.api.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.MercadoPagoConfig;
import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;
import com.sip.api.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentClient paymentClient;

    @Override
    public void processPayment() {


    }

    @Override
    public PaymentDto pay(PaymentRequestDto paymentRequestDto) {
//        Payment payment = paymentClient.create(null);
//        PreferenceShipmentsRequest preferenceShipmentsRequest = new PreferenceShipmentsRequest();
        MercadoPagoConfig.setAccessToken("PROD_ACCESS_TOKEN");
        String authToken = "";
        var values = new HashMap<String, String>() {{
            put("name", "John Doe");
            put ("occupation", "gardener");
        }};

        var objectMapper = new ObjectMapper();
        try {
            String requestBody = objectMapper
                    .writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("access_token", authToken);

        post(null, "https://api.mercadopago.com/v1/payments", authToken, null);
        return null;
    }

    @Override
    public void paymentFeedback(String paymentId) {

    }

    private <T> T post(Class<T> responseClass, String url, String authToken, Object body) {
        WebClient webClient = WebClient.builder().build();
        return webClient.post()
                .uri(url)
                .bodyValue(body)
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }

    public class PlanMPDto {
        private String reason;
        private AutoRecurringMPDto auto_recurring;
        private PaymentMethodsAllowedMPDto payment_methods_allowed;
        private String back_url;
    }

    public class AutoRecurringMPDto {
        private Integer frequency;
        private String frequency_type = "months";
        private Integer repetitions;
        private Integer billing_day;
        private boolean billing_day_proportional = true;
        private FreeTrialMPDto free_trial;
        private Integer transacion_amount;
        private String currency_id = "ARS";
    }

    public class FreeTrialMPDto {
        private Integer frequency = 1;
        private String frequency_type = "months";
    }

}
