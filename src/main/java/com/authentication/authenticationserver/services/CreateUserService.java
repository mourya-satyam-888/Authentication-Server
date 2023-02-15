package com.authentication.authenticationserver.services;

import com.authentication.CreateUserRequest;
import com.authentication.CreateUserResponse;

/**
 * The interface Create user service.
 */
public interface CreateUserService {
  /**
   * Create user create user response.
   *
   * @param request the request
   * @return the create user response
   */
  CreateUserResponse createUser(CreateUserRequest request);
}
