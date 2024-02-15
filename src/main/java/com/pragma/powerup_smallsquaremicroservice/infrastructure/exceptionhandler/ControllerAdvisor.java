package com.pragma.powerup_smallsquaremicroservice.infrastructure.exceptionhandler;

import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.DishAlreadyExistsException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.RestaurantAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    
    private static final String MESSAGE = "message";
    
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }
    
    // DOMAIN EXCEPTIONS
    @ExceptionHandler(RestaurantNameInvalidException.class)
    public ResponseEntity<Map<String, String>> handleNameInvalidException(
            RestaurantNameInvalidException ignoredRestaurantNameInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_NAME_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantNitInvalidException.class)
    public ResponseEntity<Map<String, String>> handleNitInvalidException(
            RestaurantNitInvalidException ignoredRestaurantNitInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_NIT_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantAddressInvalidException.class)
    public ResponseEntity<Map<String, String>> handleAddressInvalidException(
            RestaurantAddressInvalidException ignoredRestaurantAddressInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_ADDRESS_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantPhoneInvalidException.class)
    public ResponseEntity<Map<String, String>> handlePhoneInvalidException(
            RestaurantPhoneInvalidException ignoredRestaurantPhoneInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_PHONE_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantUrlLogoInvalidException.class)
    public ResponseEntity<Map<String, String>> handleUrlLogoInvalidException(
            RestaurantUrlLogoInvalidException ignoredRestaurantUrlLogoInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_URL_LOGO_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantIdOwnerInvalidException.class)
    public ResponseEntity<Map<String, String>> handleIdOwnerInvalidException(
            RestaurantIdOwnerInvalidException ignoredRestaurantIdOwnerInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_ID_OWNER_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFoundException(
            RestaurantNotFoundException ignoredRestaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_NOT_FOUND_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(GenericNameInvalidException.class)
    public ResponseEntity<Map<String, String>> handleGenericNameInvalidException(
            GenericNameInvalidException ignoredGenericNameInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.GENERIC_NAME_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(GenericDescriptionInvalidException.class)
    public ResponseEntity<Map<String, String>> handleGenericDescriptionInvalidException(
            GenericDescriptionInvalidException ignoredGenericDescriptionInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.GENERIC_DESCRIPTION_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException ignoredCategoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_NOT_FOUND_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFoundException(
            DishNotFoundException ignoredDishNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.DISH_NOT_FOUND_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(DishPriceInvalidException.class)
    public ResponseEntity<Map<String, String>> handleDishPriceInvalidException(
            DishPriceInvalidException ignoredDishPriceInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.DISH_PRICE_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(DishUrlImageInvalidException.class)
    public ResponseEntity<Map<String, String>> handleDishUrlImageInvalidException(
            DishUrlImageInvalidException ignoredDishUrlImageInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.DISH_URL_IMAGE_INVALID_EXCEPTION.getMessage()));
    }
    
    // --FEIGN EXCEPTIONS--
    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOwnerNotFoundException(
            OwnerNotFoundException ignoredOwnerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.OWNER_NOT_FOUND_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(RoleNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotAllowedException(
            RoleNotAllowedException ignoredRoleNotAllowedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.ROLE_NOT_ALLOWED_EXCEPTION.getMessage()));
    }
    
    // INFRASTRUCTURE EXCEPTIONS
    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException ignoredRestaurantAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_ALREADY_EXISTS_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(DishAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDishAlreadyExistsException(
            DishAlreadyExistsException ignoredDishAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.DISH_ALREADY_EXISTS_EXCEPTION.getMessage()));
    }
    
}