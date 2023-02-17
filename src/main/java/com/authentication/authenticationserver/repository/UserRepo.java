package com.authentication.authenticationserver.repository;

import com.authentication.authenticationserver.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repo.
 */
public interface UserRepo extends JpaRepository<User, String> {
  /**
   * Find by username optional.
   *
   * @param username the username
   * @return the optional
   */
  Optional<User> findByUsername(String username);
}
