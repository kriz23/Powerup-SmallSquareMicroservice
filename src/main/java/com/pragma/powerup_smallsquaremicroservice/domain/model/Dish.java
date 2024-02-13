package com.pragma.powerup_smallsquaremicroservice.domain.model;

public class Dish {
    private Long id;
    private String name;
    private Category category;
    private String description;
    private int price;
    private Restaurant restaurant;
    private String urlImage;
    private boolean available;
    
    public Dish() {
    }
    
    public Dish(Long id, String name, Category category, String description, int price, Restaurant restaurant,
                String urlImage, boolean available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
        this.urlImage = urlImage;
        this.available = available;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public String getUrlImage() {
        return urlImage;
    }
    
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
