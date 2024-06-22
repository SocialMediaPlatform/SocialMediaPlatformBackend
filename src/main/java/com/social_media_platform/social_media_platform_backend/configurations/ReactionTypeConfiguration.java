package com.social_media_platform.social_media_platform_backend.configurations;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionTypeRepository;

@Configuration
public class ReactionTypeConfiguration {
  @Bean
  CommandLineRunner ReactionTypeCommandLineRunner(ReactionTypeRepository ReactionTypeRepository) {
    if (ReactionTypeRepository.findAll().size() > 0) {
      return args -> {};
    }
    JSONParser jsonParser = new JSONParser();

    try {
      JSONArray ReactionTypes =
          (JSONArray)
              jsonParser.parse(
                  new FileReader(
                      "src/main/java/com/social_media_platform/social_media_platform_backend/databaseTables/dictValues/ReactionTypes.json"));
      return args -> {
        ReactionTypes.forEach(
            (ReactionType) -> {
              try {
                JSONObject ReactionTypeJson = (JSONObject) ReactionType;
                ReactionTypeRepository.save(
                    new ReactionType(
                        (Long) ReactionTypeJson.get("reactionTypeId"),
                        (String) ReactionTypeJson.get("reactionTypeName")));
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
      };
    } catch (Exception e) {
      throw new RuntimeException("Failed to read ReactionType.json " + e.getMessage());
    }
  }
}
