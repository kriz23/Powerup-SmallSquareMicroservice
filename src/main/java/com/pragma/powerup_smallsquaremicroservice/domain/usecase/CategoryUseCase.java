package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.ICategoryServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoryUseCase implements ICategoryServicePort {
    
    private final ICategoryPersistencePort categoryPersistencePort;
    
    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }
    
    @Override
    public List<Category> getAllCategories() {
        return categoryPersistencePort.getAllCategories();
    }
}
