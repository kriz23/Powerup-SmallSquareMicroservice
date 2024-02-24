package com.pragma.powerup_smallsquaremicroservice.domain.model;

public class RestaurantEmployee {
    private Long id;
    private Long idRestaurant;
    private Long idEmployee;
    
    public RestaurantEmployee() {
    }
    
    public RestaurantEmployee(Long id, Long idRestaurant, Long idEmployee) {
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.idEmployee = idEmployee;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdRestaurant() {
        return idRestaurant;
    }
    
    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
    
    public Long getIdEmployee() {
        return idEmployee;
    }
    
    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
}
