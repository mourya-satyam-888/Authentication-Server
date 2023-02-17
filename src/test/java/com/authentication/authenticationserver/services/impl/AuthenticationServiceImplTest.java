package com.authentication.authenticationserver.services.impl;

import com.authentication.AuthenticationRequest;
import com.authentication.authenticationserver.model.CustomUserDetails;
import com.authentication.authenticationserver.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
  @InjectMocks
  AuthenticationServiceImpl authenticationService;
  @Mock
  CustomUserDetailsService customUserDetailsService;
  @Mock
  JwtUtil jwtUtil;
  @Mock
  AuthenticationManager authenticationManager;
  @Mock
  CustomUserDetails customUserDetails;
  @Mock
  Authentication authentication;

  @Test
  void authenticateUserTestWhenValid() {
    Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
    Mockito.when(customUserDetailsService.loadUserByUsername(Mockito.anyString()))
        .thenReturn(customUserDetails);
    Mockito.when(jwtUtil.generateJwtToken(Mockito.any())).thenReturn("jwt");
    AuthenticationRequest request = AuthenticationRequest.newBuilder()
        .setUsername("user").setPassword("pass").build();
    authenticationService.authenticateUser(request);
    Mockito.verify(authenticationManager).authenticate(Mockito.any());
    Mockito.verify(customUserDetailsService).loadUserByUsername(Mockito.anyString());
    Mockito.verify(jwtUtil).generateJwtToken(Mockito.any());
  }

  @Test
  void authenticateUserTestWhenInvalid() {
    Mockito.when(authenticationManager.authenticate(Mockito.any()))
        .thenThrow(new BadCredentialsException("Not Found"));
    AuthenticationRequest request = AuthenticationRequest.newBuilder()
        .setUsername("user").setPassword("pass").build();
    Exception exception = assertThrows(BadCredentialsException.class,
        () -> authenticationService.authenticateUser(request));
    Mockito.verify(authenticationManager).authenticate(Mockito.any());
  }
}