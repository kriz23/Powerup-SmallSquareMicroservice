package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IOrderHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    
    @Override
    public void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.createOrder(authHeader, orderRequestMapper.orderRequestDtoToOrder(orderRequestDto));
    }
    
    @Override
    public Page<OrderResponseDto> getOrdersFromRestaurantByStatePageable(OrderStateEnum state, int page, int size,
                                                                         HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return orderServicePort.getOrdersFromRestaurantByStatePageable(authHeader, state, page, size)
                               .map(orderResponseMapper::orderToOrderResponseDto);
    }
    
    @Override
    public void assignEmployeeToOrder(Long idOrder, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.assignEmployeeToOrder(authHeader, idOrder);
    }
    
    @Override
    public void setOrderReady(Long idOrder, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.setOrderReady(authHeader, idOrder);
    }
}
