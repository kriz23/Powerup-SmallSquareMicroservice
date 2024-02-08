package com.pragma.powerup_smallsquaremicroservice.application.service;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OwnerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "powerup_usermicroservice", url = "${powerup_usermicroservice_url}", configuration =
        FeignClientConfiguration.class)
public class UserFeignClient {
    
    @GetMapping(value = "/owners/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    OwnerResponseDto getOwner(@PathVariable("id") Long id) {
        return null;
    }
}
