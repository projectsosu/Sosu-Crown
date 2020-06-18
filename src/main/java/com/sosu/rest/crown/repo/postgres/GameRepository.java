package com.sosu.rest.crown.repo.postgres;

import com.sosu.rest.crown.entity.postgres.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.name= :name ")
    Game getFromName(@Param("name") String name);

}
