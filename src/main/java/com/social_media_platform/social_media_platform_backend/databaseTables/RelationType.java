package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class RelationType {
  private @Id Integer relationTypeId;
  private String relationTypeName;

  @OneToMany(mappedBy = "relationType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<UserRelation> userRelations;
}
