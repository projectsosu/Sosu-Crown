package com.sosu.rest.crown.repo.mongo;

import com.sosu.rest.crown.entity.mongo.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {


}
