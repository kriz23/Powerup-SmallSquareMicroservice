package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto);
    DishResponseDto getDish(Long idDish);
    void updateDish(Long idDish, DishUpdateRequestDto dishUpdateRequestDto);
}
