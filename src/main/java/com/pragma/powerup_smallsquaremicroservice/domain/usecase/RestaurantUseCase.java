package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.Constants;
import feign.FeignException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private static final Long ADMIN_ROLE_ID = Constants.ADMIN_ROLE_ID;
    private static final Long OWNER_ROLE_ID = Constants.OWNER_ROLE_ID;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             ICategoryPersistencePort categoryPersistencePort, IDishPersistencePort dishPersistencePort,
                             IUserMSClientPort userMSClientPort, IJwtServicePort jwtServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
    }
    
    
    @Override
    public void createRestaurant(String authHeader, Restaurant restaurant) {
        if (validateRequestAdminRole(authHeader) && validateName(restaurant.getName())
                && validateNIT(restaurant.getNit()) && validateAddress(restaurant.getAddress())
                && validatePhone(restaurant.getPhone()) && validateUrlLogo(restaurant.getUrlLogo())
                && validateOwnerRoleFromRequest(authHeader, restaurant.getIdOwner())) {
            restaurantPersistencePort.createRestaurant(restaurant);
        }
    }
    
    @Override
    public Page<Restaurant> getAllRestaurantsPageable(int page, int size) {
        return restaurantPersistencePort.getAllRestaurantsPageable(page, size);
    }
    
    @Override
    public Page<Dish> getAllDishesFromRestaurantByCategoryPageable(Long idRestaurant, Long idCategory, int page, int size) {
        Page<Dish> activeDishes = null;
        if (restaurantPersistencePort.validateRestaurantExists(idRestaurant)) {
            if (idCategory != null && idCategory > 0) {
                if (categoryPersistencePort.validateCategoryExists(idCategory)) {
                    activeDishes = dishPersistencePort.getActiveDishesFromRestaurantByCategoryPageable(idRestaurant,
                                                                                                       idCategory, page,
                                                                                                       size);
                }
            } else {
                activeDishes = dishPersistencePort.getActiveDishesFromRestaurantPageable(idRestaurant, page, size);
            }
        }
        return activeDishes;
    }
    
    @Override
    public List<Restaurant> getAllRestaurantsByIdOwner(String authHeader) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestOwner = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        return restaurantPersistencePort.getAllRestaurantsByIdOwner(requestOwner.getId());
    }
    
    
    @Override
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^(?=.*[^\\d\\s])[\\w\\s]+$");
        if (!pattern.matcher(name).matches() || name.isEmpty()) {
            throw new RestaurantNameInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateNIT(String nit) {
        Pattern pattern = Pattern.compile("^\\d{5,20}$");
        if (!pattern.matcher(nit).matches() || nit.isEmpty()) {
            throw new RestaurantNitInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateAddress(String address) {
        if (address.isEmpty()) {
            throw new RestaurantAddressInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+573\\d{9}$");
        if (!pattern.matcher(phone).matches() || phone.isEmpty()) {
            throw new RestaurantPhoneInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateUrlLogo(String urlLogo) {
        if (urlLogo.isEmpty()) {
            throw new RestaurantUrlLogoInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateIdOwner(Long idOwner) {
        if (idOwner == null || idOwner <= 0) {
            throw new RestaurantIdOwnerInvalidException();
        }
        return true;
    }
    
    @Override
    public boolean validateOwnerRoleFromRequest(String authHeader, Long idOwner) {
        if (validateIdOwner(idOwner)) {
            try {
                if (!OWNER_ROLE_ID.equals(userMSClientPort.getOwnerById(authHeader, idOwner).getRole().getId())) {
                    throw new RoleNotAllowedException();
                }
            } catch (FeignException.FeignClientException e) {
                throw new OwnerNotFoundException();
            }
        }
        return true;
    }
    
    @Override
    public boolean validateRequestAdminRole(String authHeader) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        if (!ADMIN_ROLE_ID.equals(userMSClientPort.getUserByMail(authHeader, requestUserMail).getRole().getId())) {
            throw new UnauthorizedRoleException();
        }
        return true;
    }
    
    @Override
    public boolean validateRestaurantOwnership(String authHeader, Long idRestaurant) {
        if (restaurantPersistencePort.validateRestaurantExists(idRestaurant)) {
            String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
            User requestUser = userMSClientPort.getUserByMail(authHeader, requestUserMail);
            Restaurant currentRestaurant = restaurantPersistencePort.getRestaurantById(idRestaurant);
            return currentRestaurant.getIdOwner().equals(requestUser.getId());
        }
        return false;
    }
    
    @Override
    public boolean validateRestaurantOwnershipInternal(String authHeader, Long idRestaurant) {
        if (validateRestaurantOwnership(authHeader, idRestaurant)) {
            return true;
        } else {
            throw new RestaurantOwnershipInvalidException();
        }
    }
}
