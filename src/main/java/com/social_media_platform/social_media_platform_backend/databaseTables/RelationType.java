package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RelationType {
    private @Id Integer realtionTypeId;
    private String relationTypeName;
}
