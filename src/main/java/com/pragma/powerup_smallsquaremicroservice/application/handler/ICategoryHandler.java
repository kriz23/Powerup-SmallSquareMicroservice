package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryHandler {
    List<CategoryResponseDto> getAllCategories();
}
