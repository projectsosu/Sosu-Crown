package com.sosu.rest.crown.repo.mongo;

import com.sosu.rest.crown.entity.mongo.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Category findByNameAndLang(String name, String lang);

}
