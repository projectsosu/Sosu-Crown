/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.jobs;

import com.sosu.rest.backend.repo.postgres.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Performs cache operations
 */
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
            if (cacheManager.getCache(item.getCacheName()) != null) {
                try {
                    if (item.getCacheKey() != null) {
                        cacheManager.getCache(item.getCacheName()).evict(item.getCacheKey());
                        log.info("Cache cleared: {} {}", item.getCacheName(), item.getCacheKey());
                    } else {
                        cacheManager.getCache(item.getCacheName()).clear();
                        log.info("Cache cleared: {}", item.getCacheName());
                    }
                    cacheRepository.delete(item);
                } catch (Exception e) {
                    log.error("Cache clear error: {} {} {}", item.getCacheName(), item.getCacheKey(), ExceptionUtils.getStackTrace(e));
                }
            }
        });

    }
}