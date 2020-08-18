/**
 * @author : Oguz Kahraman
 * @since : 16.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entity.postgres.GameComment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCommentRepository extends PagingAndSortingRepository<GameComment, String> {

}
