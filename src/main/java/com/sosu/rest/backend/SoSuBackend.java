/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableMongoRepositories(basePackages = {"com.sosu.rest.backend.repo.mongo", "com.sosu.rest.backend.core.entity.mongo"})
@EnableJpaRepositories(basePackages = {"com.sosu.rest.backend.repo.postgres", "com.sosu.rest.backend.core.entity.postgres"})
public class SoSuBackend {

    public static void main(String[] args) {
        SpringApplication.run(SoSuBackend.class, args);
    }
}
