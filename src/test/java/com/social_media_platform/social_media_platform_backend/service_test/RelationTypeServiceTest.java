package com.social_media_platform.social_media_platform_backend.service_test;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.repositiries.RelationTypeRepository;
import com.social_media_platform.social_media_platform_backend.services.RelationTypeService;

@SpringBootTest
public class RelationTypeServiceTest {
  @Autowired RelationTypeService relationTypeService;

  @MockBean RelationTypeRepository relationTypeRepository;

  @BeforeEach
  void init() {
    relationTypeRepository.deleteAll();
  }

  @Test
  public void testGetAllRelationsTypes() {
    List<RelationType> reactionTypes =
        List.of(new RelationType(1L, "blocked"), new RelationType(2L, "follower"));
    when(relationTypeRepository.findAll()).thenReturn(reactionTypes);
    assertThat(relationTypeService.getAllRelationTypes().size()).isEqualTo(2);
    assertThat(relationTypeService.getAllRelationTypes().get(2).getRelationTypeId()).isEqualTo(2L);
  }
}
