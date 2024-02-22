package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IDishHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IDishRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IDishResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    
    @Override
    public void createDish(DishRequestDto dishRequestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        dishServicePort.createDish(authHeader, dishRequestMapper.dishRequestDtoToDish(dishRequestDto));
    }
    
    @Override
    public DishResponseDto getDishById(Long idDish) {
        return dishResponseMapper.dishToDishResponseDto(dishServicePort.getDishById(idDish));
    }
    
    @Override
    public void updateDish(Long idDish, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        dishServicePort.updateDish(authHeader, idDish, dishUpdateRequestDto.getPrice(),dishUpdateRequestDto.getDescription());
    }
}
