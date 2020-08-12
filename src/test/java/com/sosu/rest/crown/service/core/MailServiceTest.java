package com.sosu.rest.crown.service.core;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

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
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));
        mailService.sendRegisterMail("", "");
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendRegisterMail_exception() {
        doThrow(Mockito.mock(MailException.class)).when(emailSender).send(any(SimpleMailMessage.class));
        mailService.sendRegisterMail("", "");
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}