package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.EmployeeRanking;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(String authHeader, Order order);
    Page<Order> getOrdersFromRestaurantByStatePageable(String authHeader, OrderStateEnum state, int page, int size);
    void assignEmployeeToOrder(String authHeader, Long idOrder);
    void setOrderReady(String authHeader, Long idOrder);
    void setOrderDelivered(String authHeader, Long idOrder, String orderPIN);
    List<Order> getClientPendingOrders(String authHeader);
    void cancelOrder(String authHeader, Long idOrder);
    List<OrderTrace> getOrderTracesByIdOrder(String authHeader, Long idOrder);
    String getOrderDurationByIdOrder(String authHeader, Long idOrder);
    String calculateAverageDeliveredOrdersPerformanceByEmployee(Long idEmployee);
    List<EmployeeRanking> getEmployeesRanking(String authHeader, Long idRestaurant);
}
