package com.sosu.rest.crown.service.core;

import com.sosu.rest.crown.entity.mongo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    void exceptionMailSender() {
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));
        mailService.exceptionMailSender(new Exception());
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void exceptionMailSender_exception() {
        doThrow(Mockito.mock(MailException.class)).when(emailSender).send(any(SimpleMailMessage.class));
        mailService.exceptionMailSender(new Exception());
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendRegisterMail() {
        User user = new User();
        user.setEmail("example@example.com");
        when(emailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        when(messageSource.getMessage(any(), any(), any())).thenReturn("examplemessage");
        when(templateEngine.process(anyString(), any())).thenReturn("123123");
        mailService.sendRegisterMail(user, "", mock(Locale.class));
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void sendRegisterMail_exception() {
        User user = new User();
        when(emailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        mailService.sendRegisterMail(user, "", mock(Locale.class));
        verify(templateEngine, times(1)).process(anyString(), any());
    }
}