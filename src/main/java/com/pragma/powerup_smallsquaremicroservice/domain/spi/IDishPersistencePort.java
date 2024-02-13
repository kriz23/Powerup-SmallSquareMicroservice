package com.pragma.powerup_smallsquaremicroservice.domain.spi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void createDish(Dish dish);
    Dish getDish(Long idDish);
}