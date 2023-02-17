package com.authentication.authenticationserver.util;

import com.authentication.authenticationserver.constants.JwtConstants;
import com.authentication.authenticationserver.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Jwt util.
 */
@Component
public class JwtUtil {
  /**
   * The Secret.
   */
  @Value("${jwt.secret}")
  private String secret;

  /**
   * Extract username string.
   *
   * @param token the token
   * @return the string
   */
  public String extractUsername(final String token) {
    return extractAllClaims(token).getSubject();
  }

  /**
   * Extract user id string.
   *
   * @param token the token
   * @return the string
   */
  public String extractUserId(final String token) {
    return extractAllClaims(token).get(JwtConstants.USER_ID, String.class);
  }

  /**
   * Extract expiration date.
   *
   * @param token the token
   * @return the date
   */
  public Date extractExpiration(final String token) {
    return extractAllClaims(token).getExpiration();
  }

  /**
   * Extract all claims claims.
   *
   * @param token the token
   * @return the claims
   */
  private Claims extractAllClaims(final String token) {
    return Jwts.parser().setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Generate jwt token string.
   *
   * @param userDetails the user details
   * @return the string
   */
  public String generateJwtToken(final CustomUserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRY_TIME))
        .claim(JwtConstants.USER_ID, userDetails.getId())
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  /**
   * Validate jwt token boolean.
   *
   * @param token       the token
   * @param userDetails the user details
   * @return the boolean
   */
  public Boolean validateJwtToken(final String token, final CustomUserDetails userDetails) {
    final String username = extractUsername(token);
    final Boolean isTokenExpired = extractExpiration(token).before(new Date());
    return username.equals(userDetails.getUsername()) && !isTokenExpired;
  }
}
