package com.pragma.powerup_smallsquaremicroservice.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    // DOMAIN EXCEPTIONS
    NAME_INVALID_EXCEPTION("The restaurant name you entered is invalid or empty"),
    NIT_INVALID_EXCEPTION("The restaurant NIT you entered is invalid or empty"),
    ADDRESS_INVALID_EXCEPTION("The restaurant address you entered is invalid or empty"),
    PHONE_INVALID_EXCEPTION("The restaurant phone you entered is invalid or empty"),
    URL_LOGO_INVALID_EXCEPTION("The restaurant logo URL you entered is invalid or empty"),
    ID_OWNER_INVALID_EXCEPTION("The restaurant owner's ID you entered is invalid or empty"),
    // --FEIGN EXCEPTIONS--
    OWNER_NOT_FOUND_EXCEPTION("The owner you entered does not exist"),
    ROLE_NOT_ALLOWED_EXCEPTION("The user's role you entered is not allowed for this operation"),
    // INFRASTRUCTURE EXCEPTIONS
    RESTAURANT_ALREADY_EXISTS_EXCEPTION("A restaurant with that NIT already exists");
    
    private final String message;
    
    ExceptionResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}