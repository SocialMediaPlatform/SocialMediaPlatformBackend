package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.expirationTime}")
  private Long expirationTime;

  public String extractUsername(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }

  public String generateToken(User userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userDetails.getUserId());
    return generateToken(claims, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, User userDetails) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, User userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getEmail())) && !isTokenExpired(token, userDetails);
  }

  public boolean isTokenExpired(String token, UserDetails userDetails) {
    return extractExpiration(token).before(new Date(System.currentTimeMillis()));
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public Long extractUserId(String token) {
    return extractClaims(token, claims -> claims.get("userId", Long.class));
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
