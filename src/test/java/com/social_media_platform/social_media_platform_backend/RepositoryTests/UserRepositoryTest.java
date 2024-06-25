package com.social_media_platform.social_media_platform_backend.RepositoryTests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.databaseTables.RelationType;
import com.social_media_platform.social_media_platform_backend.databaseTables.UserRelation;
import com.social_media_platform.social_media_platform_backend.repositiries.RelationTypeRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRelationRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Autowired UserRelationRepository userRelationRepository;

  @Autowired RelationTypeRepository relationTypeRepository;

  @BeforeEach
  void init() {
    userRepository.deleteAll();
  }

  @Test
  void testSelectProperUserFront() {
    User user = new User("username", "email@example.com", "password");
    userRepository.save(user);
    List<User> userList = userRepository.findUsersByUsernamePattern("user", user.getUserId() + 1);
    System.out.println(userList);
    List<User> expectedUserList = List.of(user);
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectProperUserBack() {
    User user = new User("username", "email@example.com", "password");
    userRepository.save(user);
    List<User> userList = userRepository.findUsersByUsernamePattern("name", user.getUserId() + 2);
    List<User> expectedUserList = List.of(user);
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectProperUserMid() {
    User user = new User("username", "email@example.com", "password");
    userRepository.save(user);
    List<User> userList = userRepository.findUsersByUsernamePattern("rnam", user.getUserId() + 2);
    List<User> expectedUserList = List.of(user);
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectWrongUsername() {
    User user = new User("username", "email@example.com", "password");
    userRepository.save(user);
    List<User> userList =
        userRepository.findUsersByUsernamePattern("email@example.com", user.getUserId() + 2);
    List<User> expectedUserList = new ArrayList<>();
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectNonBlockedUser() {
    User user = new User("username", "email@example.com", "password");
    User targetUser = new User("halinkamalina2", "username@example.com", "password");
    userRepository.save(user);
    userRepository.save(targetUser);
    RelationType relationType = new RelationType(1L, "test");
    relationTypeRepository.save(relationType);
    UserRelation userRelation = new UserRelation(user, relationType, targetUser);
    userRelationRepository.save(userRelation);
    List<User> userList = userRepository.findUsersByUsernamePattern("malina", user.getUserId());
    List<User> expectedUserList = List.of(targetUser);
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectBlockedUser() {
    User user = new User("username", "email@example.com", "password");
    User targetUser = new User("halinkamalina2", "username@example.com", "password");
    userRepository.save(user);
    userRepository.save(targetUser);
    RelationType relationType = new RelationType(1L, "blocked");
    relationTypeRepository.save(relationType);
    UserRelation userRelation = new UserRelation(user, relationType, targetUser);
    userRelationRepository.save(userRelation);
    List<User> userList = userRepository.findUsersByUsernamePattern("rna", targetUser.getUserId());
    List<User> expectedUserList = new ArrayList<>();
    assertThat(userList).isEqualTo(expectedUserList);
  }

  @Test
  void testSelectIgnoreSearchedUser() {
    User user = new User("username", "email@example.com", "password");
    userRepository.save(user);
    List<User> userList = userRepository.findUsersByUsernamePattern("rnam", user.getUserId());
    List<User> expectedUserList = new ArrayList<>();
    assertThat(userList).isEqualTo(expectedUserList);
  }
}
