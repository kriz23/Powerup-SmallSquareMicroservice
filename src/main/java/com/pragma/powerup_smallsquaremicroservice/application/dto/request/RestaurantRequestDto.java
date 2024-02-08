package com.pragma.powerup_smallsquaremicroservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantRequestDto {
    @NotEmpty(message = "Name is required")
    @Pattern(regexp = "^(?=.*[^\\d\\s])[\\w\\s]+$", message = "Name can contain numbers but not only numbers")
    private String name;
    
    @NotEmpty(message = "NIT is required")
    @Pattern(regexp = "^\\d{5,20}$", message = "NIT numbers must be between 5 and 20 digits")
    private String nit;
    
    @NotEmpty(message = "NIT is required")
    private String address;
    
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{9,13}$", message = "The phone is not valid")
    private String phone;
    
    @NotEmpty(message = "Logo's URL is required")
    private String urlLogo;
    
    @NotEmpty(message = "Owner's id is required")
    private Long idOwner;
}
