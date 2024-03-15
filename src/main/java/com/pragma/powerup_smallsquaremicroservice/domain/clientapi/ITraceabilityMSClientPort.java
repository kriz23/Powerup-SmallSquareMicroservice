package com.pragma.powerup_smallsquaremicroservice.domain.clientapi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;

import java.util.List;

public interface ITraceabilityMSClientPort {
    void createOrderTrace(OrderTrace orderTrace);
    void updateOrderTrace(OrderTrace orderTrace);
    List<OrderTrace> getOrderTracesByIdOrder(Long idOrder);
}
