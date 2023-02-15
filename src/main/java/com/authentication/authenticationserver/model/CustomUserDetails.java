package com.authentication.authenticationserver.model;

import com.authentication.authenticationserver.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The type Custom user details.
 */
@Getter
public class CustomUserDetails implements UserDetails {
  /**
   * The Id.
   */
  private String id;
  /**
   * The Username.
   */
  private String username;
  /**
   * The Password.
   */
  private String password;
  /**
   * The Authorities.
   */
  private List<GrantedAuthority> authorities;

  /**
   * Instantiates a new Custom user details.
   *
   * @param user the user
   */
  public CustomUserDetails(final User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.authorities = new ArrayList<>();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
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
