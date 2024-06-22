package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.ReactionTypeRepository;
import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;

@Service
public class ReactionTypeService {
  private ReactionTypeRepository reactionTypeRepository;

  public ReactionTypeService(ReactionTypeRepository reactionTypeRepository) {
    this.reactionTypeRepository = reactionTypeRepository;
  }

  public List<ReactionType> getAllReactionTypes() {
    return reactionTypeRepository.findAll();
  }
}
