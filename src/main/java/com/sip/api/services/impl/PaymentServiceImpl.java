package com.sip.api.services.impl;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.sip.api.domains.payment.Payment;
import com.sip.api.domains.subscription.Subscription;
import com.sip.api.dtos.mercadopago.PaymentNotificationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.PaymentRepository;
import com.sip.api.services.PaymentService;
import com.sip.api.services.PlanService;
import com.sip.api.services.SubscriptionService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final String accessToken = "TEST-7858724244652929-060822-41b97c015ac370f0e072387aa4e86a1b-1129213662";
    private final String baseUrl = "http://34.138.26.22";
    private final String apiIP = "http://34.148.38.158";
    private final String failureUrl = "/user/pagos";
    private final UserService userService;
    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BadRequestException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPaymentsOfUser(String userId) {
        userService.findById(userId);
        return paymentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Payment> getAllPaymentsOfPlan(String planId) {
        planService.findById(planId);
        return paymentRepository.findAllByPlanId(planId);
    }

    @Override
    public Payment getPaymentBySubscriptionId(String subscriptionId) {
        subscriptionService.findSubscriptionById(subscriptionId);
        return paymentRepository.findBySubscriptionId(subscriptionId)
                .orElseThrow(() -> new BadRequestException("Subscription has not been paid yet"));
    }

    @Override
    public Preference createPaymentReference(String subscriptionId) {
        Subscription subscriptionToPay = subscriptionService.findSubscriptionById(subscriptionId);

        MercadoPagoConfig.setAccessToken(accessToken);
        PreferenceClient client = new PreferenceClient();
        List<PreferenceItemRequest> items = new ArrayList<>();
        long subscriptionDuration = ChronoUnit.MONTHS.between(subscriptionToPay.getStartDate(), subscriptionToPay.getEndDate());

        items.add(PreferenceItemRequest.builder()
                .title(subscriptionToPay.getPlan().getName())
                .description("Suscripcion a " + subscriptionToPay.getPlan().getDescription())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(subscriptionToPay.getPlan().getPrice() * subscriptionDuration))
                .build());

        PreferenceRequest request = PreferenceRequest.builder()
                .expires(false)
                .payer(PreferencePayerRequest.builder()
                        .name(subscriptionToPay.getUser().getFirstName())
                        .surname(subscriptionToPay.getUser().getLastName())
                        .email(subscriptionToPay.getUser().getEmail())
                        .build())
                // Send the subscriptionToPayId in the notification URL
                .notificationUrl(apiIP + "/payment/notification?subscriptionId=" + subscriptionId)
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success(baseUrl)
                        .pending(baseUrl)
                        .failure(baseUrl + failureUrl)
                        .build())
                .items(items)
                .build();

        // Create the preference of the subscription to pay
        Preference preference = null;
        try {
            preference = client.create(request);
        } catch (MPException | MPApiException e) {
            log.error("Error creating preference for subscription {}. Error: {}", subscriptionId, e.getMessage());
            throw new RuntimeException(e);
        }

        // Create payment entity in the subscription
        paymentRepository.save(Payment.builder()
                .transactionId(preference.getId())
                .subscription(subscriptionToPay)
                .paymentDate(LocalDate.now())
                .amountPaid(preference.getItems().get(0).getUnitPrice().doubleValue())
                .build());

        return preference;
    }

    @Override
    public void paymentNotification(String subscriptionId, PaymentNotificationDto paymentNotificationDto) {
        if (paymentNotificationDto == null)
            throw new BadRequestException("Payment notification is null");

        // Go fetch the payment data from MercadoPago
        PaymentClient paymentClient = new PaymentClient();
        com.mercadopago.resources.payment.Payment mpPayment = null;
        try {
            mpPayment = paymentClient.get(paymentNotificationDto.getData().getId());
        } catch (MPException | MPApiException e) {
            log.error("Error fetching data from payment {} from MercadoPago. Error: {}", paymentNotificationDto.getData().getId(), e.getMessage());
            throw new RuntimeException(e);
        }

        // Update the payment entity with the data from MercadoPago
        Payment paymentToUpdate = getPaymentBySubscriptionId(subscriptionId);
        paymentToUpdate.setPaymentStatus(mpPayment.getStatus());
    }
}
