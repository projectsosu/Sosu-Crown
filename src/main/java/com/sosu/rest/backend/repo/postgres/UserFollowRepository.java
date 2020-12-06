/**
 * @author : Oguz Kahraman
 * @since : 19.09.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.repo.postgres;

import com.sosu.rest.backend.model.service.FFCountModel;
import com.sosu.rest.backend.entity.postgres.UserFollow;
import com.sosu.rest.backend.entity.postgres.embeddedid.UserFollowId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRepository extends PagingAndSortingRepository<UserFollow, UserFollowId> {

    @Query(value = "select " +
            "sum(case when user_id = ?1 then 1 else 0 end) as following, " +
            "sum(case when followed_user_id = ?1 then 1 else 0 end) as follower " +
            "from follow u", nativeQuery = true)
    FFCountModel getFollowingAndFollowersCount(String userName);

    @Query(value = "select user_id from follow where followed_user_id = ?1 order by follow_date desc", nativeQuery = true)
    List<String> getFollowers(String userName);

    @Query(value = "select followed_user_id from follow where user_id = ?1 order by follow_date desc", nativeQuery = true)
    List<String> getFollowings(String userName);


}
