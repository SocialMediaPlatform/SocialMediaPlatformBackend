package com.social_media_platform.social_media_platform_backend.controller_test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import com.social_media_platform.social_media_platform_backend.controllers.UserRelationController;
import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.databaseTables.UserRelation;
import com.social_media_platform.social_media_platform_backend.services.UserRelationService;

@SpringBootTest
public class UserRelationControllerTest {
    @Autowired
    UserRelationController userRelationController;

    @MockBean
    UserRelationService userRelationService;

    @Test
    void getFollowedUsersTest() throws Exception {
        User user = new User();
        User user2 = new User();
        user.setUserId(1L);
        user2.setUserId(2L);
        RelationType relationType = new RelationType(1L, "FOLLOW");
        UserRelation userRelation = new UserRelation(user, relationType, user2);
        when(userRelationService.getfollowedUsers(1L)).thenReturn(Arrays.asList(userRelation));

        var userRelations = userRelationController.getFollowed(1L);
        assertThat(userRelations.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userRelations.getBody().size()).isEqualTo(1);
        assertThat(userRelations.getBody().get(0).getUserId()).isEqualTo(2L);
    }
}
