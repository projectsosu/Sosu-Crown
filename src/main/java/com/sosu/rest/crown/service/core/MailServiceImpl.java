package com.sosu.rest.crown.service.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    @Async
    public void exceptionMailSender(Exception ex) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("alert@suggestter.com");
            message.setTo("developer@suggestter.com");
            message.setSubject("Unhandled exception");
            message.setText("Unhandled exception detected. \n Details:\n" +
                    ex.getMessage() + "\n" + ExceptionUtils.getStackTrace(ex));
//            emailSender.send(message);
        } catch (Exception e) {
            log.error("Mail sending error: {}", e.getMessage());
        }
    }

    @Override
    @Async
    public void sendRegisterMail(String mail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("info@suggestter.com");
            message.setTo(mail);
            message.setSubject("Please confirm mail address");
            message.setText("Hello, \n\n Just one steps left. Please click link and confirm your mail\n\n" +
                    "https://www.suggester.com/confirm/" + token);
            emailSender.send(message);
        } catch (Exception e) {
            log.error("Mail sending error: {}", e.getMessage());
        }
    }
}
