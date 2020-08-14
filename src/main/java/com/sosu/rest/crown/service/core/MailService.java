/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.service.core;

import com.sosu.rest.crown.entity.mongo.User;

import java.util.Locale;

public interface MailService {

    void exceptionMailSender(Exception ex);

    void sendRegisterMail(User user, String token, Locale locale);
}
