package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    
    @Override
    public Order createOrder(Order order) {
        return orderEntityMapper.orderEntityToOrder(orderRepository.save(orderEntityMapper.orderToOrderEntity(order)));
    }
    
    @Override
    public boolean clientHasUnfinishedOrders(Long idClient) {
        List<OrderEntity> unfinishedOrders = orderRepository.findByIdClientAndStateNotIn(idClient, List.of(
                OrderStateEnum.DELIVERED, OrderStateEnum.CANCELLED));
        
        return !unfinishedOrders.isEmpty();
    }
    
    @Override
    public Page<Order> getOrdersFromRestaurantByStatePageable(Long idRestaurant, OrderStateEnum state, int page,
                                                              int size) {
        Pageable pageableParams = PageRequest.of(page, size);
        Page<OrderEntity> pageableOrders = orderRepository.findByRestaurantEntityIdAndState(idRestaurant, state, pageableParams);
        return pageableOrders.map(orderEntityMapper::orderEntityToOrder);
    }
    
    @Override
    public Order getOrderById(Long idOrder) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(NoDataFoundException::new);
        return orderEntityMapper.orderEntityToOrder(orderEntity);
    }
    
    @Override
    public void updateOrder(Order order) {
        orderRepository.save(orderEntityMapper.orderToOrderEntity(order));
    }
}
