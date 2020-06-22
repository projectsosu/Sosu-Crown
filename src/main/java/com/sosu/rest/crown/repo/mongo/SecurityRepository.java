package com.sosu.rest.crown.repo.mongo;

import com.sosu.rest.crown.entity.mongo.Security;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecurityRepository extends MongoRepository<Security, String> {

    Security findByUsernameAndToken(String name, String token);
}
