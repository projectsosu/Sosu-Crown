package com.sosu.rest.crown.core.jobs;

import com.sosu.rest.crown.repo.postgres.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SoSuCacheManager {

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 60000)
    private void checkCacheTTL() {
        cacheRepository.findAll().forEach(item -> {
            synchronized (item) {
                if (cacheManager.getCache(item.getCacheName()) != null) {
                    try {
                        if (item.getCache_key() != null) {
                            cacheManager.getCache(item.getCacheName()).evict(item.getCache_key());
                            log.info("Cache cleared: {} {}", item.getCacheName(), item.getCache_key());
                        } else {
                            cacheManager.getCache(item.getCacheName()).clear();
                            log.info("Cache cleared: {}", item.getCacheName());
                        }
                        cacheRepository.delete(item);
                    } catch (Exception e) {
                        log.error("Cache clear error: {} {} {}", item.getCacheName(), item.getCache_key(), ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        });

    }
}
