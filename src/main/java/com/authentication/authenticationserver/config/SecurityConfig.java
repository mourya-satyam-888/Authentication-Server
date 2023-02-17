package com.authentication.authenticationserver.config;

import com.authentication.authenticationserver.services.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  /**
   * The Custom user details service.
   */
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  /**
   * Authentication manager authentication manager.
   *
   * @param authenticationConfiguration the authentication configuration
   * @return the authentication manager
   * @throws Exception the exception
   */
  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Security filter chain security filter chain.
   *
   * @param http the http
   * @return the security filter chain
   * @throws Exception the exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    http.authenticationProvider(daoAuthenticationProvider());
    return http.build();
  }

  /**
   * Dao authentication provider dao authentication provider.
   *
   * @return the dao authentication provider
   */
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  /**
   * Password encoder password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
