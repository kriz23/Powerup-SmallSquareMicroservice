package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration;

import com.pragma.powerup_smallsquaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.CategoryUseCase;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.RestaurantUseCase;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter.UserFeignClientAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IUserFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.IUserMSClientResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IJwtServicePort jwtServicePort;
    private final IUserFeignClient userFeignClient;
    private final IUserMSClientResponseMapper userMSClientResponseMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

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
        return new RestaurantUseCase(restaurantPersistencePort(), userMSClientPort(), jwtServicePort);
    }
    
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }
    
    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }
    
    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishJpaAdapter(dishRepository, dishEntityMapper, restaurantRepository, categoryRepository);
    }
    
    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort(), userMSClientPort(), jwtServicePort);
    }
}