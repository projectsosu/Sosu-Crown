/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.postgres;

import com.sosu.rest.backend.entity.postgres.LikedSuggest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedSuggestRepository extends PagingAndSortingRepository<LikedSuggest, Long> {

    Long countBySuggestId(Long suggestId);

    LikedSuggest findBySuggestIdAndUserName(Long suggestId, String userName);

}
