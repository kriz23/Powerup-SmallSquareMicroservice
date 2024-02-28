package com.pragma.powerup_smallsquaremicroservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    @JsonIgnore
    private Long idRestaurant;
    private List<OrderDishRequestDto> orderDishes;
}
