package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    
    @Override
    public void createOrderDishesFromOrder(List<OrderDish> orderDishes) {
        orderDishRepository.saveAll(orderDishEntityMapper.orderDishListToOrderDishEntityList(orderDishes));
    }
    
    @Override
    public List<OrderDish> getOrderDishesByOrderId(Long idOrder) {
        return orderDishEntityMapper.orderDishEntityListToOrderDishList(orderDishRepository.findAllByOrderEntityId(idOrder));
    }
}
