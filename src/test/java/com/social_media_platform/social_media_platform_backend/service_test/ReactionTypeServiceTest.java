package com.social_media_platform.social_media_platform_backend.service_test;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionTypeRepository;
import com.social_media_platform.social_media_platform_backend.services.ReactionTypeService;

@SpringBootTest
public class ReactionTypeServiceTest {
  @Autowired ReactionTypeService reactionTypeService;

  @MockBean ReactionTypeRepository reactionTypeRepository;

  @BeforeEach
  void init() {
    reactionTypeRepository.deleteAll();
  }

  @Test
  public void testGetAllReactionTypes() {
    List<ReactionType> reactionTypes =
        List.of(new ReactionType(1L, "like"), new ReactionType(2L, "dislike"));
    when(reactionTypeRepository.findAll()).thenReturn(reactionTypes);
    assertThat(reactionTypeService.getAllReactionTypes().size()).isEqualTo(2);
    assertThat(reactionTypeService.getAllReactionTypes().get(2).getReactionTypeId()).isEqualTo(2L);
  }
}
