package com.sosu.rest.crown.repo.mongo;

import com.sosu.rest.crown.entity.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    User findByEmail(String email);

}
