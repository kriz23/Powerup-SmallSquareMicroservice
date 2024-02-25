package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.jwt;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService implements IJwtServicePort {
    
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    
    @Override
    public Key getSignInKey() {
        byte[] secretBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretBytes);
    }
    @Override
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    
    @Override
    public <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    @Override
    public Date extractExpirationFromToken(String token) {
        return extractClaimFromToken(token, Claims::getExpiration);
    }
    
    @Override
    public String getMailFromToken(String token) {
        return extractClaimFromToken(token, Claims::getSubject);
    }
    
    @Override
    public boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }
    
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String mail = getMailFromToken(token);
        return (mail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    @Override
    public String getTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
