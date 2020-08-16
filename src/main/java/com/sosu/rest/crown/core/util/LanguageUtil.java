/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.core.util;

import com.sosu.rest.crown.core.entity.postgres.CacheValidate;
import com.sosu.rest.crown.repo.mongo.LangRepository;
import com.sosu.rest.crown.repo.postgres.CacheRepository;
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
        if (locale != null && supported.contains(locale.toString())) {
            return locale.toString();
        } else {
            return "en_US";
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
