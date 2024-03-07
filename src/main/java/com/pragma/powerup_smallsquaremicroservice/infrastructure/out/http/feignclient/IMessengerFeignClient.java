package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.MessageOrderReadyRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "powerup-messengermicroservice", url = "${powerup_messengermicroservice_url}", configuration =
        FeignClientConfiguration.class)
public interface IMessengerFeignClient {
    @PostMapping("/messenger/order-ready")
    Boolean sendOrderReadyMessage(@RequestBody MessageOrderReadyRequestDto messageOrderReadyRequestDto);
}
