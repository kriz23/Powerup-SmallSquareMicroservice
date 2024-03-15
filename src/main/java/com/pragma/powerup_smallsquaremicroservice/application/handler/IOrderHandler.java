package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.EmployeeRankingResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderDurationResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    Page<OrderResponseDto> getOrdersFromRestaurantByStatePageable(OrderStateEnum state, int page, int size,
                                                                  HttpServletRequest request);
    void assignEmployeeToOrder(Long idOrder, HttpServletRequest request);
    void setOrderReady(Long idOrder, HttpServletRequest request);
    void setOrderDelivered(Long idOrder, String orderPIN, HttpServletRequest request);
    List<OrderResponseDto> getClientPendingOrders(HttpServletRequest request);
    void cancelOrder(Long idOrder, HttpServletRequest request);
    List<OrderTraceResponseDto> getOrderTracesByIdOrder(Long idOrder, HttpServletRequest request);
    OrderDurationResponseDto getOrderDurationByIdOrder(Long idOrder, HttpServletRequest request);
    List<EmployeeRankingResponseDto> getEmployeesRanking(Long idRestaurant, HttpServletRequest request);
}
