package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {
    
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.categoryEntityListToCategoryList(categoryEntityList);
    }
}
