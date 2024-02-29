package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderDishResponseDto {
    private Long id;
    private Long idOrder;
    private Long idDish;
    private int quantity;
}
