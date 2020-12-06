/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.postgres;

import com.sosu.rest.backend.entity.postgres.SuggestComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SuggestCommentRepository extends PagingAndSortingRepository<SuggestComment, Long> {

    Long countBySuggestId(Long suggestId);

    Long countByParentId(Long parentId);

    List<SuggestComment> findBySuggestIdOrderByDateDesc(Long suggestId, Pageable pageable);

    List<SuggestComment> findByParentIdOrderByDateDesc(Long parentId, Pageable pageable);

    boolean existsById(Long id);

}
