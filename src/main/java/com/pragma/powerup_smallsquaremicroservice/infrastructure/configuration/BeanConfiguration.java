package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IObjectServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IObjectPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.ObjectUseCase;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.RestaurantUseCase;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.ObjectJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IObjectRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Bean
    public IObjectPersistencePort objectPersistencePort() {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IObjectServicePort objectServicePort() {
        return new ObjectUseCase(objectPersistencePort());
    }
    
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }
    
    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }
}