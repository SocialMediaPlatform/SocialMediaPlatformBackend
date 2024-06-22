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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private User user;

  public Reaction() {}

  public Reaction(Post post, ReactionType reactionType, User user) {
    this.post = post;
    this.reactionType = reactionType;
    this.user = user;
  }

  public Reaction(Comment comment, ReactionType reactionType, User user) {
    this.comment = comment;
    this.reactionType = reactionType;
    this.user = user;
  }
}
