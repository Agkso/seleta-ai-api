package com.seletoai.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final String SECRET = "SeletoAiSuperSecretKeyForLicitacoes2026!@#SeletoAi";
  private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(String email) {
    return generateToken(Map.of(), email);
  }

  public String generateToken(Map<String, Object> extraClaims, String email) {
    return Jwts.builder()
      .setClaims(extraClaims)
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
      .signWith(getSigningKey())
      .compact();
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isTokenValid(String token, String email) {
    final String extractedEmail = extractEmail(token);
    return extractedEmail.equals(email) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}