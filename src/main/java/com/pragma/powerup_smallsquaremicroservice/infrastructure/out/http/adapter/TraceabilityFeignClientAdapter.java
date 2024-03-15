package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter;

import com.pragma.powerup_smallsquaremicroservice.application.mapper.IOrderTraceRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.ITraceabilityMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.ITraceabilityFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.ITraceabilityMSClientResponseMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityFeignClientAdapter implements ITraceabilityMSClientPort {
    private final ITraceabilityFeignClient traceabilityFeignClient;
    private final IOrderTraceRequestMapper orderTraceRequestMapper;
    private final ITraceabilityMSClientResponseMapper traceabilityMSClientResponseMapper;
    
    @Override
    public void createOrderTrace(OrderTrace orderTrace) {
        traceabilityFeignClient.createOrderTrace(orderTraceRequestMapper.orderTraceToOrderTraceRequestDto(orderTrace));
    }
    
    @Override
    public void updateOrderTrace(OrderTrace orderTrace) {
        traceabilityFeignClient.updateOrderTrace(orderTraceRequestMapper.orderTraceToOrderTraceRequestDto(orderTrace));
    }
    
    @Override
    public List<OrderTrace> getOrderTracesByIdOrder(Long idOrder) {
        return traceabilityMSClientResponseMapper.traceabilityMSOrderTraceResponseDtoListToOrderTraceList(traceabilityFeignClient.getOrderTracesByIdOrder(idOrder));
    }
    
    @Override
    public String getOrderDurationByIdOrder(Long idOrder) {
        return traceabilityFeignClient.getOrderDurationByIdOrder(idOrder);
    }
}
