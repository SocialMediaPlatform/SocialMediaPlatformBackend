package com.social_media_platform.social_media_platform_backend.configurations;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.repositiries.RelationTypeRepository;

@Configuration
public class RelationTypeConfiguration {
    @Bean
    CommandLineRunner relationTypeCommandLineRunner(RelationTypeRepository relationTypeRepository) {
        if (relationTypeRepository.findAll().size() > 0) {
            return args -> {
            };
        }
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray relationTypes = (JSONArray) jsonParser.parse(
                    new FileReader(
                            "src/main/java/com/social_media_platform/social_media_platform_backend/databaseTables/dictValues/RelationTypes.json"));
            return args -> {
                relationTypes.forEach(
                        (relationType) -> {
                            try {
                                JSONObject relationTypeJson = (JSONObject) relationType;
                                relationTypeRepository
                                        .save(new RelationType((Long) relationTypeJson.get("relationTypeId"),
                                                (String) relationTypeJson.get("realtionTypeName")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to read relationType.json " + e.getMessage());
        }
    }
}
