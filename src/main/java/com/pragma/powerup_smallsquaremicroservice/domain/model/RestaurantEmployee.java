package com.pragma.powerup_smallsquaremicroservice.domain.model;

public class RestaurantEmployee {
    private Long id;
    private Long idEmployee;
    private Long idRestaurant;
    
    public RestaurantEmployee() {
    }
    
    public RestaurantEmployee(Long id, Long idEmployee, Long idRestaurant) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.idRestaurant = idRestaurant;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdEmployee() {
        return idEmployee;
    }
    
    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
    
    public Long getIdRestaurant() {
        return idRestaurant;
    }
    
    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
    
}
