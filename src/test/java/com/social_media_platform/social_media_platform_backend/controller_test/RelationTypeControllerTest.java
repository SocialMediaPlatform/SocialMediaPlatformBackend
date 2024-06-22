package com.social_media_platform.social_media_platform_backend.controller_test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.social_media_platform.social_media_platform_backend.controllers.RelationTypeController;
import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.services.RelationTypeService;

@SpringBootTest
public class RelationTypeControllerTest {
    @Autowired
    RelationTypeController relationTypeController;

    @MockBean
    RelationTypeService relationTypeService;

    @Test
    void getAllRelationTypesTest() {
        List<RelationType> reactionTypes = List.of(new RelationType(1L, "blocked"), new RelationType(2L, "follower"));
        when(relationTypeService.getAllRelationTypes()).thenReturn(reactionTypes);
        assertThat(relationTypeController.getAllRealtionTypes().getBody().size()).isEqualTo(2);
        assertThat(relationTypeController.getAllRealtionTypes().getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
