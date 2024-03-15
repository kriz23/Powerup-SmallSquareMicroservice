package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderPersistencePort {
    Order createOrder(Order order);
    boolean clientHasUnfinishedOrders(Long idClient);
    Page<Order> getOrdersFromRestaurantByStatePageable(Long idRestaurant, OrderStateEnum state, int page, int size);
    Order getOrderById(Long idOrder);
    void updateOrder(Order order);
    List<Order> getClientPendingOrders(Long idClient);
    List<Order> getDeliveredOrdersByIdEmployee(Long idEmployee);
}
