package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenHolder {
    private static final ThreadLocal<String> authHeaderHolder = new ThreadLocal<>();
    
    public static String getAuthHeader(){
        return authHeaderHolder.get();
    }
    
    public static void setAuthHeader(String authHeader){
        authHeaderHolder.set(authHeader);
    }
    
    public static void clearAuthHeader(){
        authHeaderHolder.remove();
    }
}
