package com.sosu.rest.crown.repo.mongo;

import com.sosu.rest.crown.entity.mongo.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Category findByNameAndLang(String name, String lang);

    List<Category> findByLang(String lang);

    List<Category> findByParentId(String parentId, Sort sort);

}
