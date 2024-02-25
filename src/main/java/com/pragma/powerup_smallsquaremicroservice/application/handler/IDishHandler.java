package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto, HttpServletRequest request);
    DishResponseDto getDishById(Long idDish);
    void updateDish(Long idDish, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request);
    void updateDishStatus(Long idDish, boolean status, HttpServletRequest request);
}
