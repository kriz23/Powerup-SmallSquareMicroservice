package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {
    List<Category> getAllCategories();
}
