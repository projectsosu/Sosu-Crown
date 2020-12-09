/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.util;

import com.sosu.rest.backend.core.entity.postgres.CacheValidate;
import com.sosu.rest.backend.repo.mongo.LangRepository;
import com.sosu.rest.backend.repo.postgres.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LanguageUtil {

    @Autowired
    private LangRepository langRepository;

    @Autowired
    private CacheRepository cacheRepository;

    private static Set<String> supported = ConcurrentHashMap.newKeySet();

    @PostConstruct
    private void createLangSet() {
        langRepository.findAll().forEach(item -> supported.add(item.getName()));
    }

    public static String getLanguage(Locale locale) {
        if (locale != null && supported.contains(locale.getLanguage())) {
            return locale.getLanguage();
        } else {
            return "en";
        }
    }

    @Scheduled(fixedRate = 3600000)
    private void checkCacheTTL() {
        CacheValidate cacheValidate = cacheRepository.findByCacheName("lang");
        if (cacheValidate != null) {
            langRepository.findAll().forEach(item -> supported.add(item.getName()));
            cacheRepository.delete(cacheValidate);
        }
    }

}