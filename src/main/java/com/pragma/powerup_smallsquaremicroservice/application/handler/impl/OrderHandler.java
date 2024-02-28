package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IOrderHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IOrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    
    @Override
    public void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.createOrder(authHeader, orderRequestMapper.orderRequestDtoToOrder(orderRequestDto));
    }
}
