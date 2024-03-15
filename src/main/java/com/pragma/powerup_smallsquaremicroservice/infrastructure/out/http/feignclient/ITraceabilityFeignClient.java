package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderTraceRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceMSResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "powerup-traceabilitymicroservice", url = "${powerup_traceabilitymicroservice_url}", configuration =
        FeignClientConfiguration.class)
public interface ITraceabilityFeignClient {
    @PostMapping("/traceability/orderTrace")
    void createOrderTrace(@RequestBody OrderTraceRequestDto orderTraceRequestDto);
    
    @PutMapping("/traceability/orderTrace")
    void updateOrderTrace(@RequestBody OrderTraceRequestDto orderTraceRequestDto);
    
    @GetMapping("/traceability/orderTrace/{idOrder}")
    List<OrderTraceMSResponseDto> getOrderTracesByIdOrder(@PathVariable("idOrder") Long idOrder);
    
}
