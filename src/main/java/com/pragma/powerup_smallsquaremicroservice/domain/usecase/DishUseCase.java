package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IDishServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.DishAlreadyExistsException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IRestaurantRepository;

import java.util.List;

public class DishUseCase implements IDishServicePort {
    
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantRepository restaurantRepository;
    private final ICategoryRepository categoryRepository;
    private final IDishRepository dishRepository;
    
    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantRepository restaurantRepository,
                       ICategoryRepository categoryRepository, IDishRepository dishRepository) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.dishRepository = dishRepository;
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
    public void updateDish(Long idDish, DishUpdateRequestDto dishUpdateRequestDto) {
        DishEntity dishEntity = dishRepository.findById(idDish).orElseThrow(DishNotFoundException::new);
        if (dishRepository.findById(idDish).isPresent() && (validatePrice(
                dishUpdateRequestDto.getPrice()) && validateDescription(dishUpdateRequestDto.getDescription()))) {
            dishEntity.setPrice(dishUpdateRequestDto.getPrice());
            dishEntity.setDescription(dishUpdateRequestDto.getDescription());
            dishRepository.save(dishEntity);
            
        }
        
    }
    
    @Override
    public boolean validateName(Dish dish) {
        if (dish.getName().isEmpty()) {
            throw new GenericNameInvalidException();
        }
        List<DishEntity> dishEntityList = dishRepository.findAllByRestaurantEntityId(dish.getRestaurant().getId());
        dishEntityList.forEach(dishEntity -> {
            if (dishEntity.getName().equals(dish.getName())) {
                throw new DishAlreadyExistsException();
            }
        });
        return true;
    }
    
    @Override
    public boolean validateCategory(Long idCategory) {
        if (!categoryRepository.existsById(idCategory)) {
            throw new CategoryNotFoundException();
        }
        return true;
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
        if (!restaurantRepository.existsById(idRestaurant)) {
            throw new RestaurantNotFoundException();
        }
        return true;
    }
    
    @Override
    public boolean validateUrlImage(String urlImage) {
        if (urlImage.isEmpty()) {
            throw new DishUrlImageInvalidException();
        }
        return true;
    }
}
