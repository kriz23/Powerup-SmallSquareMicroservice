package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishPersistencePort {
    void createOrderDishesFromOrder(List<OrderDish> orderDishes);
    List<OrderDish> getOrderDishesByOrderId(Long idOrder);
}
