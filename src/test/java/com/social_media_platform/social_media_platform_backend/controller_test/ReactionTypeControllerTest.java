package com.social_media_platform.social_media_platform_backend.controller_test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.social_media_platform.social_media_platform_backend.controllers.ReactionTypeController;
import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;
import com.social_media_platform.social_media_platform_backend.services.ReactionTypeService;

@SpringBootTest
public class ReactionTypeControllerTest {
    @Autowired
    ReactionTypeController ReactionTypeController;

    @MockBean
    ReactionTypeService ReactionTypeService;

    @Test
    void getAllReactionTypesTest() {
        List<ReactionType> reactionTypes = List.of(new ReactionType(1L, "blocked"), new ReactionType(2L, "follower"));
        when(ReactionTypeService.getAllReactionTypes()).thenReturn(reactionTypes);
        assertThat(ReactionTypeController.getAllReactionTypes().getBody().size()).isEqualTo(2);
        assertThat(ReactionTypeController.getAllReactionTypes().getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
