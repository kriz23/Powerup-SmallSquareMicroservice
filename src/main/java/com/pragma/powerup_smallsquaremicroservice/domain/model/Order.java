package com.pragma.powerup_smallsquaremicroservice.domain.model;

import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long idClient;
    private String clientPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStateEnum state;
    private Long idChef;
    private Restaurant restaurant;
    private List<OrderDish> orderDishes;
    private double amount;
    private String pin;
    
    public Order() {
    }
    
    public Order(Long id, Long idClient, String clientPhone, LocalDateTime createdAt, LocalDateTime updatedAt,
                 OrderStateEnum state, Long idChef, Restaurant restaurant, List<OrderDish> orderDishes, double amount,
                 String pin) {
        this.id = id;
        this.idClient = idClient;
        this.clientPhone = clientPhone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.state = state;
        this.idChef = idChef;
        this.restaurant = restaurant;
        this.orderDishes = orderDishes;
        this.amount = amount;
        this.pin = pin;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public String getClientPhone() {
        return clientPhone;
    }
    
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public OrderStateEnum getState() {
        return state;
    }
    
    public void setState(OrderStateEnum state) {
        this.state = state;
    }
    
    public Long getIdChef() {
        return idChef;
    }
    
    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }
    
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }
    
    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
}
