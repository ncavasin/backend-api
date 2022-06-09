package com.sip.api.services.impl;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.sip.api.domains.payment.Payment;
import com.sip.api.domains.subscription.Subscription;
import com.sip.api.dtos.mercadopago.PaymentDto;
import com.sip.api.dtos.mercadopago.PaymentRequestDto;
import com.sip.api.repositories.PaymentRepository;
import com.sip.api.services.PaymentService;
import com.sip.api.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final String publicKey = "TEST-06b585dd-6c6f-4991-94fd-0cd6b7963f43";
    private final String accessToken = "TEST-7858724244652929-060822-41b97c015ac370f0e072387aa4e86a1b-1129213662";
    private final String baseUrl = "http://34.75.130.221";
    private final SubscriptionService subscriptionService;
    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPaymentsOfUser(String userId) {
        return null;
    }

    @Override
    public void createPaymentReference(String subscriptionId) {
        Subscription subscriptionToPay = subscriptionService.findSubscriptionById(subscriptionId);

        MercadoPagoConfig.setAccessToken(accessToken);
        PreferenceClient client = new PreferenceClient();
        List<PreferenceItemRequest> items = new ArrayList<>();

        items.add(PreferenceItemRequest.builder()
                .title(subscriptionToPay.getPlan().getName())
                .description(subscriptionToPay.getPlan().getDescription())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(subscriptionToPay.getPlan().getPrice()))
                .build());

        PreferenceRequest request = PreferenceRequest.builder()
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("/user/pago/:id_plan/:result")
                        .pending("/user/pago/:id_plan/:result")
                        .failure("/user/pago/:id_plan/:result")
                        .build())
                .items(items)
                .build();

        Preference preference = null;
        try {
            preference = client.create(request);
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }

        // Create payment entity in the subscription
        paymentRepository.save(Payment.builder()
                        .paymentDate(LocalDate.now())
                        .amountPaid(preference.getItems().get(0).getUnitPrice().doubleValue())
                        .transactionId(preference.getId())
                .build());


    }

    @Override
    public PaymentDto pay(PaymentRequestDto paymentRequestDto) {
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
}
