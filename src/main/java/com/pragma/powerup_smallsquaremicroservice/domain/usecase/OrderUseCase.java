package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IMessengerMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.ITraceabilityMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.*;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderUtils;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IUserMSClientPort userMSClientPort;
    private final IJwtServicePort jwtServicePort;
    private final OrderUtils orderUtils;
    private final IMessengerMSClientPort messengerMSClientPort;
    private final ITraceabilityMSClientPort traceabilityMSClientPort;
    
    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IOrderDishPersistencePort orderDishPersistencePort,
                        IRestaurantServicePort restaurantServicePort, IRestaurantPersistencePort restaurantPersistencePort,
                        IDishPersistencePort dishPersistencePort, IRestaurantEmployeeServicePort restaurantEmployeeServicePort,
                        IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort,
                        IUserMSClientPort userMSClientPort, IJwtServicePort jwtServicePort, OrderUtils orderUtils,
                        IMessengerMSClientPort messengerMSClientPort, ITraceabilityMSClientPort traceabilityMSClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantEmployeeServicePort = restaurantEmployeeServicePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.userMSClientPort = userMSClientPort;
        this.jwtServicePort = jwtServicePort;
        this.orderUtils = orderUtils;
        this.messengerMSClientPort = messengerMSClientPort;
        this.traceabilityMSClientPort = traceabilityMSClientPort;
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
        order.setClientPhone(requestClient.getPhone());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setState(OrderStateEnum.PENDING);
        double totalPrice = orderUtils.calculateTotalPrice(order.getOrderDishes());
        order.setAmount(totalPrice);
        
        Order createdOrder = orderPersistencePort.createOrder(order);
        order.getOrderDishes().forEach(orderDish -> orderDish.setOrder(createdOrder));
        orderDishPersistencePort.createOrderDishesFromOrder(order.getOrderDishes());
        OrderTrace orderTrace = new OrderTrace(
                null, createdOrder.getId(), createdOrder.getIdClient(), requestClient.getMail(), null,
                createdOrder.getState().getState(), null, null, null, createdOrder.getUpdatedAt()
        );
        traceabilityMSClientPort.createOrderTrace(orderTrace);
    }
    
    @Override
    public Page<Order> getOrdersFromRestaurantByStatePageable(String authHeader, OrderStateEnum state, int page,
                                                              int size) {
        Page<Order> orders = null;
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestEmployee = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestEmployee.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestEmployee.getId());
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
        User requestEmployee = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestEmployee.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestEmployee.getId());
            Order existingOrder = orderPersistencePort.getOrderById(idOrder);
            if (!existingOrder.getRestaurant().getId().equals(idRestaurant)){
                throw new EmployeeInvalidOperationException();
            }
            if (existingOrder.getIdChef() == null && existingOrder.getState() == OrderStateEnum.PENDING){
                existingOrder.setIdChef(requestEmployee.getId());
                existingOrder.setState(existingOrder.getState().nextState());
                existingOrder.setUpdatedAt(LocalDateTime.now());
                orderPersistencePort.updateOrder(existingOrder);
                OrderTrace orderTrace = new OrderTrace(
                        null, existingOrder.getId(), null, null, OrderStateEnum.PENDING.getState(),
                        OrderStateEnum.PREPARING.getState(), existingOrder.getIdChef(), requestEmployee.getMail(), null,
                        existingOrder.getUpdatedAt()
                );
                traceabilityMSClientPort.updateOrderTrace(orderTrace);
            }
        }
    }
    
    @Override
    public void setOrderReady(String authHeader, Long idOrder) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestEmployee = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestEmployee.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestEmployee.getId());
            Restaurant currentRestaurant = restaurantPersistencePort.getRestaurantById(idRestaurant);
            Order existingOrder = orderPersistencePort.getOrderById(idOrder);
            if (!existingOrder.getRestaurant().getId().equals(idRestaurant) || !existingOrder.getIdChef().equals(requestEmployee.getId())){
                throw new EmployeeInvalidOperationException();
            }
            if (existingOrder.getState() == OrderStateEnum.PREPARING){
                existingOrder.setPin(orderUtils.generateOrderPIN());
                existingOrder.setState(existingOrder.getState().nextState());
                existingOrder.setUpdatedAt(LocalDateTime.now());
                orderPersistencePort.updateOrder(existingOrder);
                OrderTrace orderTrace = new OrderTrace(
                        null, existingOrder.getId(), null, null, OrderStateEnum.PREPARING.getState(),
                        OrderStateEnum.READY.getState(), null, null, null,
                        existingOrder.getUpdatedAt()
                );
                traceabilityMSClientPort.updateOrderTrace(orderTrace);
                if (!messengerMSClientPort.sendOrderReadyMessage(existingOrder.getClientPhone(), currentRestaurant.getPhone(),
                                                                 currentRestaurant.getName(), existingOrder.getPin())){
                    throw new MessageNotSentException();
                }
            }
        }
    }
    
    @Override
    public void setOrderDelivered(String authHeader, Long idOrder, String orderPIN) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestEmployee = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        if (restaurantEmployeeServicePort.validateEmployeeExistsInternal(requestEmployee.getId())){
            Long idRestaurant = restaurantEmployeeServicePort.getRestaurantId(requestEmployee.getId());
            Order existingOrder = orderPersistencePort.getOrderById(idOrder);
            if (!existingOrder.getRestaurant().getId().equals(idRestaurant)){
                throw new EmployeeInvalidOperationException();
            }
            if (existingOrder.getState() == OrderStateEnum.READY && existingOrder.getPin().equals(orderPIN)){
                existingOrder.setState(existingOrder.getState().nextState());
                existingOrder.setUpdatedAt(LocalDateTime.now());
                orderPersistencePort.updateOrder(existingOrder);
                OrderTrace orderTrace = new OrderTrace(
                        null, existingOrder.getId(), null, null, OrderStateEnum.READY.getState(),
                        OrderStateEnum.DELIVERED.getState(), null, null, null,
                        existingOrder.getUpdatedAt()
                );
                traceabilityMSClientPort.updateOrderTrace(orderTrace);
            } else {
                throw new OrderInvalidDeliveryException();
            }
        }
    }
    
    @Override
    public List<Order> getClientPendingOrders(String authHeader) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestClient = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        return orderPersistencePort.getClientPendingOrders(requestClient.getId());
    }
    
    @Override
    public void cancelOrder(String authHeader, Long idOrder) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestClient = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        Order existingOrder = orderPersistencePort.getOrderById(idOrder);
        if  (!existingOrder.getIdClient().equals(requestClient.getId())){
            throw new ClientInvalidOperationException();
        }
        if (existingOrder.getState().isCancelable()){
            existingOrder.setState(OrderStateEnum.CANCELLED);
            existingOrder.setUpdatedAt(LocalDateTime.now());
            orderPersistencePort.updateOrder(existingOrder);
            OrderTrace orderTrace = new OrderTrace(
                    null, existingOrder.getId(), null, null, OrderStateEnum.PENDING.getState(),
                    OrderStateEnum.CANCELLED.getState(), null, null, null,
                    existingOrder.getUpdatedAt()
            );
            traceabilityMSClientPort.updateOrderTrace(orderTrace);
        } else {
            throw new OrderNotCancelableException();
            // If needed, put the call to messengerMSClientPort here
        }
    }
    
    @Override
    public List<OrderTrace> getOrderTracesByIdOrder(String authHeader, Long idOrder) {
        String requestUserMail = jwtServicePort.getMailFromToken(jwtServicePort.getTokenFromHeader(authHeader));
        User requestClient = userMSClientPort.getUserByMail(authHeader, requestUserMail);
        Order existingOrder = orderPersistencePort.getOrderById(idOrder);
        if  (!existingOrder.getIdClient().equals(requestClient.getId())){
            throw new ClientInvalidOperationException();
        }
        return traceabilityMSClientPort.getOrderTracesByIdOrder(idOrder);
    }
    
    @Override
    public String getOrderDurationByIdOrder(String authHeader, Long idOrder) {
        Order existingOrder = orderPersistencePort.getOrderById(idOrder);
        if (restaurantServicePort.validateRestaurantOwnershipInternal(authHeader, existingOrder.getRestaurant().getId())){
            return traceabilityMSClientPort.getOrderDurationByIdOrder(idOrder);
        }
        return null;
    }
    
    @Override
    public String calculateAverageDeliveredOrdersPerformanceByEmployee(Long idEmployee) {
        List<Order> employeeOrders = orderPersistencePort.getDeliveredOrdersByIdEmployee(idEmployee);
        List<Duration> averageDurations = new ArrayList<>();
        
        for (Order order : employeeOrders) {
            String orderDuration = traceabilityMSClientPort.getOrderDurationByIdOrder(order.getId());
            averageDurations.add(Duration.parse(orderDuration));
        }
        Duration average = Duration.ZERO;
        for (Duration duration : averageDurations) {
            average = average.plus(duration);
        }
        if (!averageDurations.isEmpty()) {
            average = average.dividedBy(averageDurations.size());
        }
        return average.toString();
    }
    
    @Override
    public List<EmployeeRanking> getEmployeesRanking(String authHeader, Long idRestaurant) {
        List<EmployeeRanking> employeesRanking = new ArrayList<>();
        if (restaurantServicePort.validateRestaurantOwnershipInternal(authHeader, idRestaurant)){
            List<RestaurantEmployee> employees =
                    restaurantEmployeePersistencePort.getEmployeesByIdRestaurant(idRestaurant);
            for (RestaurantEmployee employee : employees){
                String averageEmployeePerformance = calculateAverageDeliveredOrdersPerformanceByEmployee(employee.getIdEmployee());
                EmployeeRanking employeeRanking = new EmployeeRanking(employee.getIdEmployee(), averageEmployeePerformance);
                employeesRanking.add(employeeRanking);
            }
            employeesRanking.sort(Comparator.comparing(e -> Duration.parse(e.getAverageOrdersPerformance())));
        }
        return employeesRanking;
    }
}
