package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.RestaurantUseCase;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter.UserFeignClientAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IUserFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.IUserMSClientResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserFeignClient userFeignClient;
    private final IUserMSClientResponseMapper userMSClientResponseMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    
    @Bean
    public IUserMSClientPort userMSClientPort() {
        return new UserFeignClientAdapter(userFeignClient, userMSClientResponseMapper);
    }
    
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }
    
    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userMSClientPort());
    }
}