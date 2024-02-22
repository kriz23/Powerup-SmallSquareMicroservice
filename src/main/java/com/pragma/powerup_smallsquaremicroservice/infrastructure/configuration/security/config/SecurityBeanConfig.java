package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.config;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.jwt.TokenHolder;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.models.CustomUserDetails;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IUserFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.IUserMSClientResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class SecurityBeanConfig {
    
    private final IUserFeignClient userFeignClient;
    private final IUserMSClientResponseMapper userMSClientResponseMapper;
    
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new CustomUserDetails(userMSClientResponseMapper.userMSResponseDtoToUser(
                        userFeignClient.getUserByMail(TokenHolder.getAuthHeader(), username)));
            }
        };
    }
}
