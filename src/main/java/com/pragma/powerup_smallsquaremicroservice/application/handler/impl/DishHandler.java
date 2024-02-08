package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IDishHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IDishRequestMapper;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.IDishResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    
    @Override
    public void createDish(DishRequestDto dishRequestDto) {
        dishServicePort.createDish(dishRequestMapper.dishRequestDtoToDish(dishRequestDto));
    }
    
    @Override
    public DishResponseDto getDish(Long idDish) {
        return dishResponseMapper.dishToDishResponseDto(dishServicePort.getDish(idDish));
    }
}
