/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entity.postgres.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    Game findByName(@Param("name") String name);

    @Query("SELECT g FROM Game g WHERE g.categoryId like %:categoryId% or g.mainCategoryId like %:categoryId% or " +
            "g.consoleCategoryId like %:categoryId%")
    List<Game> findByCategoryId(@Param("categoryId") String categoryId,
                                Pageable pageable);

    List<Game> findTop10ByAndNameContains(@Param("name") String name);

    @Query("SELECT g FROM Game g order by function('RAND')")
    List<Game> findRandomGame(Pageable pageable);

    Integer countByCategoryIdContaining(String categoryId);

    Integer countByConsoleCategoryIdContaining(String consoleCategoryId);

    Integer countByMainCategoryIdContaining(String categoryId);


}
