/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.service.core;

import com.sosu.rest.backend.entity.mongo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Mail service
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MessageSource messageSource;

    /**
     * This methods send information mail to all developer for quick solution
     *
     * @param ex throwed exception
     */
    @Override
    @Async
    public void exceptionMailSender(Exception ex) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@suggestter.com");
            message.setTo("developer@suggestter.com");
            message.setSubject("Unhandled exception");
            message.setText("Unhandled exception detected. \n Details:\n" +
                    ex.getMessage() + "\n" + ExceptionUtils.getStackTrace(ex));
            emailSender.send(message);
        } catch (MailException e) {
            log.error("Mail sending error: {}", e.getMessage());
        }
    }

    /**
     * This methods send validation mail after success register
     *
     * @param user  user entity
     * @param token validation token
     */
    @Override
    @Async
    public void sendRegisterMail(User user, String token, Locale locale) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
            Context context = new Context();
            context.setVariable("token", token);
            context.setVariable("name", user.getName());
            context.setVariable("username", user.getUsername());
            context.setLocale(locale);
            String html = templateEngine.process("validation", context);
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject(messageSource.getMessage("mail.validation.subject", null, locale));
            helper.setFrom("no-reply@suggestter.com");
            emailSender.send(message);
        } catch (Exception e) {
            log.error("Mail sending error: {}", e.getMessage());
        }
    }
}
