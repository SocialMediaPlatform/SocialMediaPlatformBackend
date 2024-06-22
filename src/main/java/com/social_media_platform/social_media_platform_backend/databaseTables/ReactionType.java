package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class ReactionType {
  private @Id Long reactionTypeId;
  private String reactionTypeName;

  @OneToMany(mappedBy = "reactionType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Reaction> reactions;

  public ReactionType(Long reactionTypeId, String reactionTypeName) {
    this.reactionTypeId = reactionTypeId;
    this.reactionTypeName = reactionTypeName;
  }

  public ReactionType() {
  }
}
