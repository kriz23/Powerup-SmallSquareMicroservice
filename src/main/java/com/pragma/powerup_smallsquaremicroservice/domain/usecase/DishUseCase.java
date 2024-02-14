package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishPriceInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishUrlImageInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.GenericDescriptionInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {
    
    private final IDishPersistencePort dishPersistencePort;
    
    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }
    
    @Override
    public void createDish(Dish dish) {
        dish.setAvailable(true);
        
        if (validateName(dish) && validateCategory(dish.getCategory().getId()) && validateDescription(
                dish.getDescription()) && validatePrice(dish.getPrice()) && validateRestaurant(
                dish.getRestaurant().getId()) && validateUrlImage(dish.getUrlImage())) {
            dishPersistencePort.createDish(dish);
        }
        
    }
    
    @Override
    public Dish getDish(Long idDish) {
        return dishPersistencePort.getDish(idDish);
    }
    
    @Override
    public boolean validateName(Dish dish) {
        return dishPersistencePort.validateName(dish);
    }
    
    @Override
    public boolean validateCategory(Long idCategory) {
        return dishPersistencePort.validateCategory(idCategory);
    }
    
    @Override
    public boolean validateDescription(String description) {
        if (description.isEmpty()) {
            throw new GenericDescriptionInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validatePrice(int price) {
        if (price <= 0) {
            throw new DishPriceInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateRestaurant(Long idRestaurant) {
        return dishPersistencePort.validateRestaurant(idRestaurant);
    }
    
    @Override
    public boolean validateUrlImage(String urlImage) {
        if (urlImage.isEmpty()) {
            throw new DishUrlImageInvalidException();
        }
        return true;
    }
}
