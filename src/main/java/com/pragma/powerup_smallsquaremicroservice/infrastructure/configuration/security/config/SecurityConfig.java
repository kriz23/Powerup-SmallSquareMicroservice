package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.config;

import com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private static final String[] WHITE_LIST_URL = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/smallsquare"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests(req -> req
                    .antMatchers(WHITE_LIST_URL).permitAll()
                    .antMatchers("/api/v1/smallsquare/categories").hasRole("ADMIN")
                    .antMatchers("/api/v1/smallsquare/restaurants").hasAnyRole("ADMIN", "PROPIETARIO", "EMPLEADO")
                    .antMatchers("/api/v1/smallsquare/dishes").hasAnyRole("ADMIN", "PROPIETARIO", "EMPLEADO")
                    .antMatchers("/api/v1/smallsquare/clients").hasAnyRole("CLIENTE")
                    .antMatchers("/api/v1/smallsquare/employees").hasAnyRole("EMPLEADO")
                    .anyRequest().authenticated())
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        
        return http.build();
    }
}
