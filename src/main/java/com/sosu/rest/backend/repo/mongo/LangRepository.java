/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.mongo;

import com.sosu.rest.backend.core.entity.mongo.SupportedLangs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangRepository extends MongoRepository<SupportedLangs, String> {

}