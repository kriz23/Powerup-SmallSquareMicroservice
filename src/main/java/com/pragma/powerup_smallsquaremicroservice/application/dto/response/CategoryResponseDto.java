package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
}
