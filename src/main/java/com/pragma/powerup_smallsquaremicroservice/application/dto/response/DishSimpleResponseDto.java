package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishSimpleResponseDto {
    private Long id;
    private String name;
    private Long idCategory;
    private String description;
    private int price;
    private Long idRestaurant;
    private String urlImage;
}
