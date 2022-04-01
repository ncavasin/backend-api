package com.sip.api.services;

public interface MailSender {

    void sendMail(String to, String subject, String body);
}
