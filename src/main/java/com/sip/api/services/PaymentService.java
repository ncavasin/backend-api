package com.sip.api.services;

import com.mercadopago.resources.preference.Preference;
import com.sip.api.domains.payment.Payment;
import com.sip.api.dtos.mercadopago.PaymentNotificationDto;

import java.util.List;

public interface PaymentService {

    List<Payment> getAll();

    Payment findById(String paymentId);

    List<Payment> getAllPaymentsOfUser(String userId);

    List<Payment> getAllPaymentsOfPlan(String planId);

    Payment getPaymentBySubscriptionId(String subscriptionId);

    Preference createPaymentReference(String subscriptionId);

    void paymentNotification(String subscriptionId, PaymentNotificationDto paymentNotificationDto);
}
