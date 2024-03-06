package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.*;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderUtils;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    private final OrderUtils orderUtils;
    
    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IOrderDishPersistencePort orderDishPersistencePort,
                        IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort,
                        IRestaurantEmployeeServicePort restaurantEmployeeServicePort, IUserMSClientPort userMSClientPort,
                        IJwtServicePort jwtServicePort, OrderUtils orderUtils) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantEmployeeServicePort = restaurantEmployeeServicePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
        this.orderUtils = orderUtils;
    }
    
    @Override
    public void createOrder(String authHeader, Order order) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestClient = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (!restaurantPersistencePort.validateRestaurantExists(order.getRestaurant().getId())){
            throw new RestaurantNotFoundException();
        }
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());
        if (requestClient.getPhone().equals(restaurant.getPhone())){
            throw new ClientRestaurantEqualsPhoneException();
        }
        
        if (orderPersistencePort.clientHasUnfinishedOrders(requestClient.getId())){
            throw new ClientHasUnfinishedOrderException();
        }
        
        List<Long> idsDishes = order.getOrderDishes().stream().map(OrderDish::getDish).map(Dish::getId).collect(
                Collectors.toList());
        
        Set<Long> idsDishesSet = new HashSet<>(idsDishes);
        
        if (idsDishes.size() != idsDishesSet.size()){
            throw new ClientOrderInvalidException();
        }
        
        List<Dish> dishesFromDatabase = dishPersistencePort.getActiveDishesFromRestaurantByDishesIds(
                order.getRestaurant().getId(), idsDishes);
        
        if (dishesFromDatabase.size() != idsDishes.size()){
            throw new DishInOrderInvalidException();
        }
        
        for (OrderDish orderDish : order.getOrderDishes()) {
            if (orderDish.getQuantity() <= 0){
                throw new ClientOrderInvalidException();
            }
        }
        
        order.setIdClient(requestClient.getId());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setState(OrderStateEnum.PENDING);
        double totalPrice = orderUtils.calculateTotalPrice(order.getOrderDishes());
        order.setAmount(totalPrice);
        
        Order createdOrder = orderPersistencePort.createOrder(order);
        order.getOrderDishes().forEach(orderDish -> orderDish.setOrder(createdOrder));
        orderDishPersistencePort.createOrderDishesFromOrder(order.getOrderDishes());
    }
    
    @Override
    public Page<Order> getOrdersFromRestaurantByStatePageable(String authHeader, OrderStateEnum state, int page,
                                                              int size) {
        Page<Order> orders = null;
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestUser = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestUser.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestUser.getId());
            if (state == null){
                throw new StateFilterEmptyException();
            } else {
                orders = orderPersistencePort.getOrdersFromRestaurantByStatePageable(idRestaurant, state, page, size);
                for (Order order : orders){
                    order.setOrderDishes(orderDishPersistencePort.getOrderDishesByOrderId(order.getId()));
                }
            }
        }
        return orders;
    }
    
    @Override
    public void assignEmployeeToOrder(String authHeader, Long idOrder) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestUser = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestUser.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestUser.getId());
            Order existingOrder = orderPersistencePort.getOrderById(idOrder);
            if (!existingOrder.getRestaurant().getId().equals(idRestaurant)){
                throw new EmployeeInvalidOperationException();
            }
            if (existingOrder.getIdChef() == null && existingOrder.getState() == OrderStateEnum.PENDING){
                existingOrder.setIdChef(requestUser.getId());
                existingOrder.setState(existingOrder.getState().nextState());
                orderPersistencePort.updateOrder(existingOrder);
            }
        }
    }
}
