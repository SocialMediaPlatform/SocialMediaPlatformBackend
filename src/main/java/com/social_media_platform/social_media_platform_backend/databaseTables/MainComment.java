package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@DiscriminatorValue("MAIN")
public class MainComment extends Comment {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId")
  private Post post;

  @OneToMany(mappedBy = "mainComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<SubComment> subComments;
}
