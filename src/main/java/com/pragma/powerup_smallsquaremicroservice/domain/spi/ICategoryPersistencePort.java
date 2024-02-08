package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {
    List<Category> getAllCategories();
}
