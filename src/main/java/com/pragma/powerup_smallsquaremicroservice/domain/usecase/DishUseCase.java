package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishPriceInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishUrlImageInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.GenericDescriptionInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.RestaurantOwnershipInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {
    
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    
    public DishUseCase(IDishPersistencePort dishPersistencePort,
                       IRestaurantPersistencePort restaurantPersistencePort,
                       IUserMSClientPort userMSClientPort,
                       IJwtServicePort jwtServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
    }
    
    @Override
    public void createDish(String authHeader, Dish dish) {
        dish.setAvailable(true);
        
        if (validateRestaurantOwnership(authHeader, dish.getRestaurant().getId()) && validateName(dish)
                && validateCategoryExists(dish.getCategory().getId()) && validateDescription(dish.getDescription())
                && validatePrice(dish.getPrice()) && validateUrlImage(dish.getUrlImage())) {
            dishPersistencePort.createDish(dish);
        }
        
    }
    
    @Override
    public Dish getDishById(Long idDish) {
        return dishPersistencePort.getDishById(idDish);
    }
    
    @Override
    public void updateDish(String authHeader, Long idDish, int dishPrice, String dishDescription) {
        Dish existingDish = getDishById(idDish);
        if (validateRestaurantOwnership(authHeader, existingDish.getRestaurant().getId())
            && (validatePrice(dishPrice) && validateDescription(dishDescription))) {
                existingDish.setPrice(dishPrice);
                existingDish.setDescription(dishDescription);
                dishPersistencePort.updateDish(existingDish);
        }
    }
    
    @Override
    public boolean validateName(Dish dish) {
        return dishPersistencePort.validateName(dish);
    }
    
    @Override
    public boolean validateCategoryExists(Long idCategory) {
        return dishPersistencePort.validateCategoryExists(idCategory);
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
    public boolean validateRestaurantExists(Long idRestaurant) {
        return dishPersistencePort.validateRestaurantExists(idRestaurant);
    }
    
    @Override
    public boolean validateUrlImage(String urlImage) {
        if (urlImage.isEmpty()) {
            throw new DishUrlImageInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateRestaurantOwnership(String authHeader, Long idRestaurant) {
        if (validateRestaurantExists(idRestaurant)){
            String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
            User requestUser = userMSClientPort.getUserByMail(authHeader, requestUserMail);
            Restaurant currentRestaurant = restaurantPersistencePort.getRestaurantById(idRestaurant);
            if (!currentRestaurant.getIdOwner().equals(requestUser.getId())){
                throw new RestaurantOwnershipInvalidException();
            }
            return true;
        }
        return false;
    }
}
