package com.pragma.powerup_smallsquaremicroservice.domain.api;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;

public interface IDishServicePort {
    void createDish(String authHeader, Dish dish);
    Dish getDishById(Long idDish);
    void updateDish(String authHeader, Long idDish, int dishPrice, String dishDescription);
    boolean validateName(Dish dish);
    boolean validateCategoryExists(Long idCategory);
    boolean validateDescription(String description);
    boolean validatePrice(int price);
    boolean validateUrlImage(String urlImage);
}
