package com.pragma.powerup_smallsquaremicroservice.infrastructure.configuration;

import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderTraceRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.*;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IMessengerMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.ITraceabilityMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup_smallsquaremicroservice.domain.usecase.*;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderUtils;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter.MessengerFeignClientAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter.TraceabilityFeignClientAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter.UserFeignClientAdapter;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IMessengerFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.ITraceabilityFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IUserFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.ITraceabilityMSClientResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.IUserMSClientResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter.*;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.*;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IJwtServicePort jwtServicePort;
    private final IUserFeignClient userFeignClient;
    private final IMessengerFeignClient messengerFeignClient;
    private final ITraceabilityFeignClient traceabilityFeignClient;
    private final IUserMSClientResponseMapper userMSClientResponseMapper;
    private final IOrderTraceRequestMapper orderTraceRequestMapper;
    private final ITraceabilityMSClientResponseMapper traceabilityMSClientResponseMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    
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
        return new RestaurantUseCase(restaurantPersistencePort(), categoryPersistencePort(), dishPersistencePort(),
                                     userMSClientPort(), jwtServicePort);
    }
    
    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }
    
    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
    
    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper, restaurantRepository, categoryRepository);
    }
    
    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), restaurantServicePort());
    }
    
    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }
    
    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), restaurantServicePort(),
                                             userMSClientPort(), jwtServicePort);
    }
    
    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort(){
        return new OrderDishJpaAdapter(orderDishRepository, orderDishEntityMapper);
    }
    
    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }
    
    @Bean
    public OrderUtils orderUtils(){
        return new OrderUtils(orderPersistencePort(), dishPersistencePort());
    }
    
    @Bean
    public IMessengerMSClientPort messengerMSClientPort(){
        return new MessengerFeignClientAdapter(messengerFeignClient);
    }
    
    @Bean
    public ITraceabilityMSClientPort traceabilityMSClientPort(){
        return new TraceabilityFeignClientAdapter(traceabilityFeignClient, orderTraceRequestMapper, traceabilityMSClientResponseMapper);
    }
    
    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), orderDishPersistencePort(), restaurantServicePort(),
                                restaurantPersistencePort(), dishPersistencePort(), restaurantEmployeeServicePort(),
                                restaurantEmployeePersistencePort(), userMSClientPort(), jwtServicePort, orderUtils(),
                                messengerMSClientPort(), traceabilityMSClientPort());
    }
}