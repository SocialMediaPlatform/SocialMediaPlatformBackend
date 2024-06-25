package com.social_media_platform.social_media_platform_backend.databaseTables;

import lombok.Data;
import lombok.ToString;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"user", "comment", "reactions"})
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  private Date postDate;
  private String postContent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  @ToString.Exclude
  private User user;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private Set<MainComment> comment;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private Set<Reaction> reactions;

  public Post(Date postDate, String postContent) {
    this.postDate = postDate;
    this.postContent = postContent;
  }

  public Post() {}
}