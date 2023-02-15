package com.authentication.authenticationserver.services.impl;

import com.authentication.AuthenticationRequest;
import com.authentication.AuthenticationResponse;
import com.authentication.authenticationserver.services.AuthenticationService;
import com.authentication.authenticationserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * The type Authentication service.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  /**
   * The Custom user details service.
   */
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  /**
   * The Jwt util.
   */
  @Autowired
  private JwtUtil jwtUtil;
  /**
   * The Authentication manager.
   */
  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public AuthenticationResponse authenticateUser(final AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(), request.getPassword()));
    final String jwtToken = jwtUtil.generateJwtToken(
        customUserDetailsService.loadUserByUsername(request.getUsername()));
    return AuthenticationResponse.newBuilder().setJwtToken(jwtToken).build();
  }
}
