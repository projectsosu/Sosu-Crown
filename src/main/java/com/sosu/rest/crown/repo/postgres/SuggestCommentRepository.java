/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entity.postgres.SuggestComment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SuggestCommentRepository extends PagingAndSortingRepository<SuggestComment, Long> {

    Long countBySuggestId(Long suggestId);

}
