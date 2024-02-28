package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;

import javax.servlet.http.HttpServletRequest;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
}
