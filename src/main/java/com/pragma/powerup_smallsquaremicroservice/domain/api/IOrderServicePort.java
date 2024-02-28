package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;

public interface IOrderServicePort {
    void createOrder(String authHeader, Order order);
}
