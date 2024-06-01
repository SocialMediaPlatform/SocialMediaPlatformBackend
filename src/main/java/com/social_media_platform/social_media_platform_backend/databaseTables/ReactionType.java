package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class ReactionType {
    private @Id Integer reactionTypeId;
    private String reactionTypeName;

    @OneToMany(mappedBy = "reactionType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reaction> reactions;
}
