package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("SUB")
public class SubComment extends Comment {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mainCommentId")
  private MainComment mainComment;
}
