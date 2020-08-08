package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.core.entity.CacheValidate;
import org.springframework.data.repository.CrudRepository;

public interface CacheRepository extends CrudRepository<CacheValidate, Long> {

}
