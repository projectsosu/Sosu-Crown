package com.sosu.rest.crown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableMongoRepositories(basePackages = "com.sosu.rest.crown.repo.mongo")
@EnableJpaRepositories(basePackages = "com.sosu.rest.crown.repo.postgres")
public class CrownApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrownApplication.class, args);
    }
}
