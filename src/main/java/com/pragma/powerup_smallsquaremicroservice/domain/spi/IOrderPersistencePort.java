package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;

public interface IOrderPersistencePort {
    Order createOrder(Order order);
    boolean clientHasUnfinishedOrders(Long idClient);
}
