package com.social_media_platform.social_media_platform_backend.databaseTables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@EqualsAndHashCode(exclude = {"conversationMessages", "conversations"})
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String username;
  private String email;
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<UserRelation> userRelations;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Post> posts;
  
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<ConversationMessage> conversationMessages;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Reaction> reactions;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_conversation",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "conversation_id")
  )
  private Set<Conversation> conversations = new HashSet<>();

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
