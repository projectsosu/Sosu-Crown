/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.mongo;

import com.sosu.rest.backend.entity.mongo.Category;
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
