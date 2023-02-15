package com.authentication.authenticationserver.services;

import com.authentication.AuthenticationRequest;
import com.authentication.AuthenticationResponse;

/**
 * The interface Authentication service.
 */
public interface AuthenticationService {

  /**
   * Authenticate user authentication response.
   *
   * @param request the request
   * @return the authentication response
   */
  AuthenticationResponse authenticateUser(AuthenticationRequest request);
}
