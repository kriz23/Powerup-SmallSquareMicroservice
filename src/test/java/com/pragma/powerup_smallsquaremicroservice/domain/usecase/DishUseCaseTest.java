package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishPriceInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.DishUrlImageInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.GenericDescriptionInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {
    @Mock
    private IDishPersistencePort dishPersistencePort;
    
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    
    @InjectMocks
    private DishUseCase dishUseCase;
    
    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantServicePort);
    }
    
    @Test
    void createDish_allValid_callsPersistencePort() {
        String authHeader = "validHeader";
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "Descripción", 10000,
                             new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                            1L), "www.image.com", true);
        when(dishPersistencePort.validateName(dish)).thenReturn(true);
        when(dishPersistencePort.validateCategoryExists(dish.getCategory().getId())).thenReturn(true);
        when(restaurantServicePort.validateRestaurantOwnershipInternal(authHeader, dish.getRestaurant().getId())).thenReturn(true);
        dishUseCase.createDish(authHeader, dish);
        verify(dishPersistencePort, times(1)).createDish(dish);
    }
    
    @Test
    void getDish_callsPersistencePort() {
        dishUseCase.getDishById(1L);
        verify(dishPersistencePort, times(1)).getDishById(1L);
    }
    
    @Test
    void updateDish_allValid_callsPersistencePort() {
        String authHeader = "validHeader";
        Dish existingDish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "Descripción", 10000,
                             new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                            1L), "www.image.com", true);
        when(dishPersistencePort.getDishById(1L)).thenReturn(existingDish);
        when(restaurantServicePort.validateRestaurantOwnershipInternal(authHeader, existingDish.getRestaurant().getId())).thenReturn(true);
        dishUseCase.updateDish(authHeader, 1L, 15000, "Descripción actualizada");
        verify(dishPersistencePort, times(1)).updateDish(existingDish);
    }
    
    @Test
    void updateDishStatus_allValid_callsPersistencePort(){
        String authHeader = "validHeader";
        Dish existingDish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "Descripción", 10000,
                                     new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                                    1L), "www.image.com", true);
        when(dishPersistencePort.getDishById(1L)).thenReturn(existingDish);
        when(restaurantServicePort.validateRestaurantOwnershipInternal(authHeader, existingDish.getRestaurant().getId())).thenReturn(true);
        
        dishUseCase.updateDishStatus(authHeader, 1L, false);
        
        verify(dishPersistencePort, times(1)).updateDish(existingDish);
    }
    
    @Test
    void validateDescription_invalidDescription_throwsException(){
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "", 10000,
                             new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                            1L), "www.image.com", true);
        String description = dish.getDescription();
        assertThrows(GenericDescriptionInvalidException.class, () -> dishUseCase.validateDescription(description));
    }
    
    @Test
    void validatePrice_invalidPrice_throwsException(){
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "Descripción", 0,
                             new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                            1L), "www.image.com", true);
        int price = dish.getPrice();
        assertThrows(DishPriceInvalidException.class, () -> dishUseCase.validatePrice(price));
    }
    
    @Test
    void validateUrlImage_invalidUrlImage_throwsException(){
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"), "Descripción", 10000,
                             new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573101234567", "www.logo.com",
                                            1L), "", true);
        String urlImage = dish.getUrlImage();
        assertThrows(DishUrlImageInvalidException.class, () -> dishUseCase.validateUrlImage(urlImage));
    }
}