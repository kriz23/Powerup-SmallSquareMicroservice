package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    Page<OrderResponseDto> getOrdersFromRestaurantByStatePageable(OrderStateEnum state, int page, int size,
                                                                  HttpServletRequest request);
}
