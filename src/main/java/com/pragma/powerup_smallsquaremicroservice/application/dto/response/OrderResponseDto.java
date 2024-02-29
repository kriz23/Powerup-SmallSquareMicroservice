package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStateEnum state;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderDishResponseDto> orderDishes;
    private double amount;
    private String pin;
}
