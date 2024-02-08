package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishResponseDto {
    private Long id;
    private String name;
    private CategoryResponseDto category;
    private String description;
    private int price;
    private RestaurantResponseDto restaurant;
    private String urlImage;
}
