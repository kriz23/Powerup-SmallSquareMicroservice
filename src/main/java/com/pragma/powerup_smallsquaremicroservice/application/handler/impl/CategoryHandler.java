package com.pragma.powerup_smallsquaremicroservice.application.handler.impl;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.CategoryResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.ICategoryHandler;
import com.pragma.powerup_smallsquaremicroservice.application.mapper.ICategoryResponseMapper;
import com.pragma.powerup_smallsquaremicroservice.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapper;
    
    
    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryResponseMapper.categoryToCategoryResponseDtoList(categoryServicePort.getAllCategories());
    }
}
