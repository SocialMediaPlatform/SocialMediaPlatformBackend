package com.social_media_platform.social_media_platform_backend.databaseTables;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Reaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reactionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reactionTypeId", nullable = false)
  private ReactionType reactionType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "commentId")
  private Comment comment;
}
