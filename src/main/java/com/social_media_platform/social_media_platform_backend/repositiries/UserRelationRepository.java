package com.social_media_platform.social_media_platform_backend.repositiries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.UserRelation;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, Integer> {
  @Query(
      "SELECT ur FROM UserRelation ur WHERE ur.user.userId = :userId and"
          + " ur.relationType.relationTypeId = 1")
  List<UserRelation> findFollowedUsers(Long userId);

  @Query(
      value =
          "Select case when exists (Select * from user_relation ur where user_id = :firstUserId and"
              + " target_user_id = :secondUserId and relation_type_id = 3) then Cast(1 as BIT) else"
              + " CAST(0 AS BIT) end",
      nativeQuery = true)
  boolean areUsersBlocked(Long firstUserId, Long secondUserId);

  @Query(
      value =
          "Select case when exists (Select * from user_relation ur where user_id = :firstUserId and"
              + " target_user_id = :secondUserId and relation_type_id = 1) then Cast(1 as BIT) else"
              + " CAST(0 AS BIT) end",
      nativeQuery = true)
  boolean areUsersFollowed(Long firstUserId, Long secondUserId);
}
