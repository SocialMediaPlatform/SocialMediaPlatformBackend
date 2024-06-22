package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRelation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long relationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "relationTypeId", nullable = false)
  private RelationType relationType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "targetUserId", nullable = false)
  private User targetUser;

  public UserRelation() {
  }

  public UserRelation(User user, RelationType relationType, User targetUser) {
    this.user = user;
    this.relationType = relationType;
    this.targetUser = targetUser;
  }
}
