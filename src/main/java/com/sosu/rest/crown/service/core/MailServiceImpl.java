package com.sosu.rest.crown.service.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void exceptionMailSender(Exception ex) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("info@sosu.com");
            message.setTo("oguzkahraman52@gmail.com");
            message.setSubject("Unhandled exception");
            message.setText(ex.getMessage() + "\n" + ExceptionUtils.getStackTrace(ex));
            emailSender.send(message);
        } catch (Exception e) {
            log.error("Mail sending error: {}", e.getMessage());
        }
    }
}
