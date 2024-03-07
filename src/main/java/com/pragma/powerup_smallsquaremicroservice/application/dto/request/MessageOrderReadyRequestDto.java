package com.pragma.powerup_smallsquaremicroservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageOrderReadyRequestDto {
    private String clientPhone;
    private String restaurantPhone;
    private String restaurantName;
    private String orderPIN;
}
