package com.authentication.authenticationserver.services.impl;

import com.authentication.authenticationserver.entity.User;
import com.authentication.authenticationserver.model.CustomUserDetails;
import com.authentication.authenticationserver.repository.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type Custom user details service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  /**
   * The User repo.
   */
  @Autowired
  private UserRepo userRepo;

  @Override
  public CustomUserDetails loadUserByUsername(final String username)
      throws UsernameNotFoundException {
    final Optional<User> user = userRepo.findByUsername(username);
    user.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));
    return user.map(CustomUserDetails::new).get();
  }

  /**
   * Is user present boolean.
   *
   * @param username the username
   * @return the boolean
   */
  public boolean isUserPresent(final String username) {
    return userRepo.findByUsername(username).isPresent();
  }
}
