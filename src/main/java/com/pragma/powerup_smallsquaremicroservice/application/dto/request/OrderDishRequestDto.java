package com.pragma.powerup_smallsquaremicroservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderDishRequestDto {
    private Long idDish;
    private int quantity;
}
