package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "comment")
public abstract class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  private String commentContents;
  private Date commentDate;

  // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  // @JoinColumn(name = "reactionId")
  // private Set<Reaction> reaction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private User user;
}
