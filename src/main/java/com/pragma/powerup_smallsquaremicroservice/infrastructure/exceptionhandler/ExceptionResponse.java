package com.pragma.powerup_smallsquaremicroservice.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    // DOMAIN EXCEPTIONS
    UNAUTHORIZED_ROLE_EXCEPTION("Your current role is not allowed for this operation"),
    RESTAURANT_OWNERSHIP_INVALID_EXCEPTION("You cannot perform this operation because you are not the owner of the restaurant"),
    RESTAURANT_NAME_INVALID_EXCEPTION("The restaurant name you entered is invalid or empty"),
    RESTAURANT_NIT_INVALID_EXCEPTION("The restaurant NIT you entered is invalid or empty"),
    RESTAURANT_ADDRESS_INVALID_EXCEPTION("The restaurant address you entered is invalid or empty"),
    RESTAURANT_PHONE_INVALID_EXCEPTION("The restaurant phone you entered is invalid or empty"),
    RESTAURANT_URL_LOGO_INVALID_EXCEPTION("The restaurant logo URL you entered is invalid or empty"),
    RESTAURANT_ID_OWNER_INVALID_EXCEPTION("The restaurant owner's ID you entered is invalid or empty"),
    RESTAURANT_NOT_FOUND_EXCEPTION("The restaurant you are looking for does not exist"),
    GENERIC_NAME_INVALID_EXCEPTION("The name you entered is invalid or empty"),
    GENERIC_DESCRIPTION_INVALID_EXCEPTION("The description you entered is invalid or empty"),
    CATEGORY_NOT_FOUND_EXCEPTION("The category you are looking for does not exist"),
    DISH_NOT_FOUND_EXCEPTION("The dish you are looking for does not exist"),
    DISH_PRICE_INVALID_EXCEPTION("The price you entered is invalid or empty"),
    DISH_URL_IMAGE_INVALID_EXCEPTION("The dish image URL you entered is invalid or empty"),
    // --FEIGN EXCEPTIONS--
    OWNER_NOT_FOUND_EXCEPTION("The owner you entered does not exist"),
    ROLE_NOT_ALLOWED_EXCEPTION("The user's role you entered is not allowed for this operation"),
    // INFRASTRUCTURE EXCEPTIONS
    PAGINATION_INVALID_EXCEPTION("The pagination you entered is invalid"),
    REQUEST_PARAM_INVALID_EXCEPTION("You entered a invalid request parameter"),
    RESTAURANT_ALREADY_EXISTS_EXCEPTION("A restaurant with that NIT already exists"),
    DISH_ALREADY_EXISTS_EXCEPTION("A dish with that name already exists in the restaurant"),
    DISH_AVAILABLE_STATUS_INVALID_EXCEPTION("The dish status you entered is invalid (not a boolean) should be 'true' " +
                                                    "or 'false'");
    
    private final String message;
    
    ExceptionResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}