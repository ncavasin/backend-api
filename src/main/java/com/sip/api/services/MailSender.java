package com.sip.api.services;

public interface MailSender {

    void sendConfirmationMail(String to, String firstName, String token);
}
