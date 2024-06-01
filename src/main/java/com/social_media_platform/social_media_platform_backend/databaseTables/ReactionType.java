package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ReactionType {
    private @Id Integer reactionTypeId;
    private String reactionTypeName;
}
