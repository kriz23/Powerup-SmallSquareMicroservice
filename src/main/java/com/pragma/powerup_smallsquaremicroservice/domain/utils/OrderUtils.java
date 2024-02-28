package com.pragma.powerup_smallsquaremicroservice.domain.utils;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Dish;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUtils {
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    
    public OrderUtils(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }
    
    public double calculateTotalPrice(List<OrderDish> orderDishes){
        double totalPrice = 0;
        for (OrderDish orderDish : orderDishes){
            Dish dishFromOrderDishesList = orderDish.getDish();
            Dish dishFromDatabase = dishPersistencePort.getDishById(dishFromOrderDishesList.getId());
            if (dishFromDatabase != null){
                totalPrice += dishFromDatabase.getPrice() * orderDish.getQuantity();
            }
        }
        return totalPrice;
    }
}
