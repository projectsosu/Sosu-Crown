/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.core.entity.postgres.CacheValidate;
import org.springframework.data.repository.CrudRepository;

public interface CacheRepository extends CrudRepository<CacheValidate, Long> {

    CacheValidate findByCacheName(String name);

}
