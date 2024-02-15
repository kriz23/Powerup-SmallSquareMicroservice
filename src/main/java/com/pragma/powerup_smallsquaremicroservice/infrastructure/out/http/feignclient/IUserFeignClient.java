package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OwnerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "powerup-usermicroservice", url = "${powerup_usermicroservice_url}", configuration =
        FeignClientConfiguration.class)
public interface IUserFeignClient {
    @GetMapping("/users/owners/{id}")
    OwnerResponseDto getOwnerById(@PathVariable("id") Long id);
}
