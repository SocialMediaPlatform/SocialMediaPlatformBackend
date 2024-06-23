package com.social_media_platform.social_media_platform_backend.service_test;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.databaseTables.UserRelation;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRelationRepository;
import com.social_media_platform.social_media_platform_backend.services.UserRelationService;

@SpringBootTest
public class UserRelationServiceTest {
  @Autowired UserRelationService userRelationService;

  @MockBean UserRelationRepository userRelationRepository;

  @Test
  void getFollowedUsers() throws Exception {
    User user = new User();
    User user2 = new User();
    user.setUserId(1L);
    user2.setUserId(2L);
    RelationType relationType = new RelationType(1L, "FOLLOW");
    UserRelation userRelation = new UserRelation(user, relationType, user2);
    when(userRelationRepository.findFollowedUsers(1L)).thenReturn(Arrays.asList(userRelation));

    var userRelations = userRelationService.getfollowedUsers(1L);

    assert (userRelations.size() == 1);
    assert (userRelations.get(0).getTargetUser().getUserId() == 2L);
  }
}
