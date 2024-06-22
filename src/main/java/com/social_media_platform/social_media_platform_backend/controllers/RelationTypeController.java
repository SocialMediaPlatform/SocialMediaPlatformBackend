package com.social_media_platform.social_media_platform_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.services.RelationTypeService;

@RestController
@RequestMapping("/api/v1/relationType")
public class RelationTypeController {
    private RelationTypeService relationTypeService;

    public RelationTypeController(RelationTypeService relationTypeService) {
        this.relationTypeService = relationTypeService;
    }

    @GetMapping
    public ResponseEntity<List<RelationType>> getAllRealtionTypes() {
        return new ResponseEntity<>(relationTypeService.getAllRelationTypes(), HttpStatus.OK);
    }
}
