package com.authentication.authenticationserver.services.impl;

import com.authentication.CreateUserRequest;
import com.authentication.authenticationserver.entity.User;
import com.authentication.authenticationserver.exceptions.ResourceException;
import com.authentication.authenticationserver.repository.UserRepo;
import com.authentication.authenticationserver.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceImplTest {
  @InjectMocks
  CreateUserServiceImpl createUserService;
  @Mock
  CustomUserDetailsService customUserDetailsService;
  @Mock
  UserRepo userRepo;
  @Mock
  JwtUtil jwtUtil;
  @Mock
  PasswordEncoder passwordEncoder;
  @Mock
  User user;

  @Test
  void createUserWhenAlreadyExists() {
    Mockito.when(customUserDetailsService
        .isUserPresent(Mockito.anyString())).thenReturn(true);
    CreateUserRequest createUserRequest = CreateUserRequest.newBuilder()
        .setUsername("user").setPassword("pass").build();
    Exception exception = assertThrows(ResourceException.class,
        () -> createUserService.createUser(createUserRequest));
    Mockito.verify(customUserDetailsService).isUserPresent(Mockito.anyString());
  }

  @Test
  void createUserWhenValid() {
    Mockito.when(customUserDetailsService
        .isUserPresent(Mockito.anyString())).thenReturn(false);
    Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("pass");
    Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
    Mockito.when(jwtUtil.generateJwtToken(Mockito.any())).thenReturn("jwt");
    CreateUserRequest createUserRequest = CreateUserRequest.newBuilder()
        .setUsername("user").setPassword("pass").build();
    createUserService.createUser(createUserRequest);
    Mockito.verify(customUserDetailsService).isUserPresent(Mockito.anyString());
    Mockito.verify(passwordEncoder).encode(Mockito.anyString());
    Mockito.verify(userRepo).save(Mockito.any());
    Mockito.verify(jwtUtil).generateJwtToken(Mockito.any());
  }
}