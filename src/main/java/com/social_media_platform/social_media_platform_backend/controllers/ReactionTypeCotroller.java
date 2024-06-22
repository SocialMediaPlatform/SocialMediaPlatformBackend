package com.social_media_platform.social_media_platform_backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;
import com.social_media_platform.social_media_platform_backend.services.ReactionTypeService;

@RestController
@RequestMapping("/api/v1/reactionType")
public class ReactionTypeCotroller {
    private ReactionTypeService reactionTypeService;

    public ReactionTypeCotroller(ReactionTypeService reactionTypeService) {
        this.reactionTypeService = reactionTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ReactionType>> getAllReactionTypes() {
        return new ResponseEntity<>(reactionTypeService.getAllReactionTypes(), HttpStatus.OK);
    }
}
