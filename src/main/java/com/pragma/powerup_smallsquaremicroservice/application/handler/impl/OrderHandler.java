package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.EmployeeRankingResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderDurationResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IOrderHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IEmployeeRankingResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderTraceResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.model.EmployeeRanking;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderTraceResponseMapper orderTraceResponseMapper;
    private final IEmployeeRankingResponseMapper employeeRankingResponseMapper;
    
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
    
    @Override
    public void setOrderDelivered(Long idOrder, String orderPIN, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.setOrderDelivered(authHeader, idOrder, orderPIN);
    }
    
    @Override
    public List<OrderResponseDto> getClientPendingOrders(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return orderResponseMapper.orderListToOrderResponseDtoList(orderServicePort.getClientPendingOrders(authHeader));
    }
    
    @Override
    public void cancelOrder(Long idOrder, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        orderServicePort.cancelOrder(authHeader, idOrder);
    }
    
    @Override
    public List<OrderTraceResponseDto> getOrderTracesByIdOrder(Long idOrder, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return orderTraceResponseMapper.orderTraceListToOrderTraceResponseDtoList(orderServicePort.getOrderTracesByIdOrder(authHeader, idOrder));
    }
    
    @Override
    public OrderDurationResponseDto getOrderDurationByIdOrder(Long idOrder, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return new OrderDurationResponseDto(formatDuration(orderServicePort.getOrderDurationByIdOrder(authHeader, idOrder)));
    }
    
    @Override
    public List<EmployeeRankingResponseDto> getEmployeesRanking(Long idRestaurant, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        List<EmployeeRanking> employeesRanking = orderServicePort.getEmployeesRanking(authHeader, idRestaurant);
        formatDurationInEmployeeRankingResponseDtoList(employeesRanking);
        return employeeRankingResponseMapper.employeeRankingListToEmployeeRankingResponseDtoList(employeesRanking);
    }
    
    private String formatDuration(String averageDuration){
        Duration duration = Duration.parse(averageDuration);
        LocalTime time = LocalTime.MIDNIGHT.plus(duration);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }
    
    private void formatDurationInEmployeeRankingResponseDtoList(List<EmployeeRanking> employeesRanking){
        employeesRanking.forEach(employeeRanking -> employeeRanking.setAverageOrdersPerformance(formatDuration(employeeRanking.getAverageOrdersPerformance())));
    }
}
