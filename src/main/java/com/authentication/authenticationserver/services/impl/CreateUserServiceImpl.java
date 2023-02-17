package com.authentication.authenticationserver.services.impl;

import com.authentication.CreateUserRequest;
import com.authentication.CreateUserResponse;
import com.authentication.authenticationserver.constants.ExceptionMessageConstants;
import com.authentication.authenticationserver.entity.User;
import com.authentication.authenticationserver.exceptions.ResourceException;
import com.authentication.authenticationserver.repository.UserRepo;
import com.authentication.authenticationserver.services.CreateUserService;
import com.authentication.authenticationserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type Create user service.
 */
@Service
public class CreateUserServiceImpl implements CreateUserService {
  /**
   * The Custom user details service.
   */
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  /**
   * The User repo.
   */
  @Autowired
  private UserRepo userRepo;
  /**
   * The Jwt util.
   */
  @Autowired
  private JwtUtil jwtUtil;
  /**
   * The Password encoder.
   */
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public CreateUserResponse createUser(final CreateUserRequest request) {
    if (customUserDetailsService.isUserPresent(request.getUsername())) {
      throw new ResourceException(ExceptionMessageConstants.USERNAME_EXISTS);
    }
    final User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    userRepo.save(user);
    final String jwtToken = jwtUtil.generateJwtToken(
        customUserDetailsService.loadUserByUsername(request.getUsername()));
    return CreateUserResponse.newBuilder().setJwtToken(jwtToken).build();
  }
}
