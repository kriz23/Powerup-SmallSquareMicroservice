package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    void createOrder(String authHeader, Order order);
    Page<Order> getOrdersFromRestaurantByStatePageable(String authHeader, OrderStateEnum state, int page, int size);
}
