package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.RelationTypeRepository;
import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;

@Service
public class RelationTypeService {
    private RelationTypeRepository relationTypeRepository;

    public RelationTypeService(RelationTypeRepository relationTypeRepository) {
        this.relationTypeRepository = relationTypeRepository;
    }

    public List<RelationType> getAllRelationTypes() {
        return relationTypeRepository.findAll();
    }
}
