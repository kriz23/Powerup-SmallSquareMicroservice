package com.pragma.powerup_smallsquaremicroservice.infrastructure.exceptionhandler;

import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
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
    @ExceptionHandler(NameInvalidException.class)
    public ResponseEntity<Map<String, String>> handleNameInvalidException(
            NameInvalidException ignoredNameInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.NAME_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(NITInvalidException.class)
    public ResponseEntity<Map<String, String>> handleNitInvalidException(
            NITInvalidException ignoredNitInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.NIT_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(AddressInvalidException.class)
    public ResponseEntity<Map<String, String>> handleAddressInvalidException(
            AddressInvalidException ignoredAddressInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.ADDRESS_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(PhoneInvalidException.class)
    public ResponseEntity<Map<String, String>> handlePhoneInvalidException(
            PhoneInvalidException ignoredPhoneInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.PHONE_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(UrlLogoInvalidException.class)
    public ResponseEntity<Map<String, String>> handleUrlLogoInvalidException(
            UrlLogoInvalidException ignoredUrlLogoInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.URL_LOGO_INVALID_EXCEPTION.getMessage()));
    }
    
    @ExceptionHandler(IdOwnerInvalidException.class)
    public ResponseEntity<Map<String, String>> handleIdOwnerInvalidException(
            IdOwnerInvalidException ignoredIdOwnerInvalidException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.ID_OWNER_INVALID_EXCEPTION.getMessage()));
    }
    
    // INFRASTRUCTURE EXCEPTIONS
    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException ignoredRestaurantAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap(MESSAGE, ExceptionResponse.RESTAURANT_ALREADY_EXISTS_EXCEPTION.getMessage()));
    }
    
}