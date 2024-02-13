package com.pragma.powerup_smallsquaremicroservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishRequestDto {
    private String name;
    private Long idCategory;
    private String description;
    private int price;
    @JsonIgnore
    private Long idRestaurant;
    private String urlImage;
}
