package com.sosu.rest.crown.service.core;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

    void exceptionMailSender(Exception ex);

    @Async
    void sendRegisterMail(String mail, String token);
}
