package com.pragma.powerup_smallsquaremicroservice.domain.api;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface IJwtServicePort {
    Key getSignInKey();
    Claims getAllClaimsFromToken(String token);
    <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver);
    Date extractExpirationFromToken(String token);
    String getMailFromToken(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String getTokenFromHeader(String authHeader);
}
