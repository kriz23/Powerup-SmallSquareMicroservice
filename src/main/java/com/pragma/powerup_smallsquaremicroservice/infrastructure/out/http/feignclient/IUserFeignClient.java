package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.UserMSResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "powerup-usermicroservice", url = "${powerup_usermicroservice_url}", configuration =
        FeignClientConfiguration.class)
public interface IUserFeignClient {
    @GetMapping("/users/owners/{id}")
    UserMSResponseDto getOwnerById(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Long id);
    
    @GetMapping("/users/mail/{mail}")
    UserMSResponseDto getUserByMail(@RequestHeader("Authorization") String bearerToken,@PathVariable("mail") String mail);
    
}
