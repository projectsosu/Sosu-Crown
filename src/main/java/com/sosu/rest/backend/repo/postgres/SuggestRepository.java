/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.postgres;

import com.sosu.rest.backend.entity.postgres.Suggest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestRepository extends PagingAndSortingRepository<Suggest, Long> {

    List<Suggest> findByUserName(String userName, Pageable pageable);

    boolean existsById(Long id);

}
