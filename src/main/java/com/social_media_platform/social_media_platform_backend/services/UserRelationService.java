package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.UserRelation;
import com.social_media_platform.social_media_platform_backend.repositiries.RelationTypeRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRelationRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

@Service
public class UserRelationService {
  private final UserRelationRepository relationRepository;
  private final RelationTypeRepository relationTypeRepository;
  private final UserRepository userRepository;

  public UserRelationService(
      UserRelationRepository relationRepository,
      RelationTypeRepository relationTypeRepository,
      UserRepository userRepository) {
    this.relationRepository = relationRepository;
    this.relationTypeRepository = relationTypeRepository;
    this.userRepository = userRepository;
  }

  public void setUserRelation(Long userId, Long targetId, Long relation) throws Exception {
    var user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    var targetUser =
        userRepository
            .findById(targetId)
            .orElseThrow(() -> new Exception("Target user user not found"));
    var relationType =
        relationTypeRepository
            .findById(relation)
            .orElseThrow(() -> new Exception("Relation type not found"));

    relationRepository.save(new UserRelation(user, relationType, targetUser));
  }

  public List<UserRelation> getfollowedUsers(Long userId) throws Exception {
    var user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    return relationRepository.findFollowedUsers(userId);
  }
}
