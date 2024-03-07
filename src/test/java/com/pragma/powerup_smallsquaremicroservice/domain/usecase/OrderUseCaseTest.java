package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.api.IJwtServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IMessengerMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.*;
import com.pragma.powerup_smallsquaremicroservice.domain.model.*;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderUseCaseTest {
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    
    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;
    
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IDishPersistencePort dishPersistencePort;
    
    @Mock
    private IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    
    @Mock
    private IUserMSClientPort userMSClientPort;
    
    @Mock
    private IJwtServicePort jwtServicePort;
    
    @Mock
    private OrderUtils orderUtils;
    
    @Mock
    private IMessengerMSClientPort messengerMSClientPort;
    
    @InjectMocks
    private OrderUseCase orderUseCase;
    
    @Test
    void createOrder_allValid_callsPersistencePort(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING,
                                null, restaurant , List.of(orderDish), 20000, "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        when(orderPersistencePort.clientHasUnfinishedOrders(4L)).thenReturn(false);
        when(dishPersistencePort.getActiveDishesFromRestaurantByDishesIds(1L, List.of(1L))).thenReturn(List.of(dish));
        when(orderUtils.calculateTotalPrice(List.of(orderDish))).thenReturn(20000.0);
        
        orderUseCase.createOrder(authHeader, order);
        
        verify(orderPersistencePort, times(1)).createOrder(order);
        verify(orderDishPersistencePort, times(1)).createOrderDishesFromOrder(List.of(orderDish));
    }
    
    @Test
    void createOrder_restaurantNotFound_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING,
                                null, restaurant , List.of(orderDish), 20000, "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(false);
        assertThrows(RestaurantNotFoundException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void createOrder_clientHasSamePhoneAsRestaurant_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING,
                                null, restaurant , List.of(orderDish), 20000, "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573101234567",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        
        assertThrows(ClientRestaurantEqualsPhoneException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void createOrder_clientHasUnfinishedOrders_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PENDING, null, restaurant , List.of(orderDish), 20000, "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        when(orderPersistencePort.clientHasUnfinishedOrders(4L)).thenReturn(true);
        
        assertThrows(ClientHasUnfinishedOrderException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void createOrder_invalidOrder_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        OrderDish orderDish2 = new OrderDish(1L, new Order(), dish, 3);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PENDING, null, restaurant , List.of(orderDish, orderDish2), 20000,
                                "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        when(orderPersistencePort.clientHasUnfinishedOrders(4L)).thenReturn(false);
        
        assertThrows(ClientOrderInvalidException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void createOrder_invalidOrderDish_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        Dish dish2 = new Dish(2L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", false);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, 2);
        OrderDish orderDish2 = new OrderDish(2L, new Order(), dish2, 3);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PENDING, null, restaurant , List.of(orderDish, orderDish2), 20000,
                                "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        when(orderPersistencePort.clientHasUnfinishedOrders(4L)).thenReturn(false);
        when(dishPersistencePort.getActiveDishesFromRestaurantByDishesIds(1L, List.of(1L, 2L))).thenReturn(List.of(dish));
        
        assertThrows(DishInOrderInvalidException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void createOrder_invalidOrderDishQuantity_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        Dish dish = new Dish(1L, "Dish", new Category(1L, "Categoría", "Descripción"),
                             "Descripción", 10000, restaurant, "www.image.com", true);
        OrderDish orderDish = new OrderDish(1L, new Order(), dish, -2);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING,
                                null, restaurant , List.of(orderDish), 20000, "1234");
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(4L, "John", "Doe", "123456789", "+573107654321",
                                     LocalDate.of(2000, 1, 1), "client@mail.com", "password",
                                     new Role(4L, "ROLE_CLIENTE", "Admin")));
        when(restaurantPersistencePort.validateRestaurantExists(1L)).thenReturn(true);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        when(orderPersistencePort.clientHasUnfinishedOrders(4L)).thenReturn(false);
        when(dishPersistencePort.getActiveDishesFromRestaurantByDishesIds(1L, List.of(1L))).thenReturn(List.of(dish));
        
        assertThrows(ClientOrderInvalidException.class, () -> orderUseCase.createOrder(authHeader, order));
    }
    
    @Test
    void getOrdersFromRestaurantByStatePageable_allValid_withState_callsPersistencePort(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        List<Order> orderList = List.of(
                new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING, null,
                          new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573107654321",
                                         "www.logo.com", 2L),
                          List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish", new Category(1L,"Categoría","Descripción"),
                                                                              "Descripción", 10000,
                                                                              new Restaurant(1L, "Restaurant",
                                                                                             "123456789", "Calle 123",
                                                                                             "+573107654321","www.logo.com",2L),
                                                                                              "www.image.com", true),
                                        2)), 20000, "1234"));
        Page<Order> orders = new PageImpl<>(orderList);
        when(orderPersistencePort.getOrdersFromRestaurantByStatePageable(1L, OrderStateEnum.PENDING, 0, 10))
                .thenReturn(orders);
        
        orderUseCase.getOrdersFromRestaurantByStatePageable(authHeader, OrderStateEnum.PENDING, 0, 10);
        verify(orderPersistencePort, times(1)).getOrdersFromRestaurantByStatePageable(1L, OrderStateEnum.PENDING, 0, 10);
        verify(orderDishPersistencePort, times(1)).getOrderDishesByOrderId(1L);
    }
    
    @Test
    void getOrdersFromRestaurantByStatePageable_allValid_withoutState_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        
        assertThrows(StateFilterEmptyException.class, () -> orderUseCase.getOrdersFromRestaurantByStatePageable(authHeader, null, 0, 10));
    }
    
    @Test
    void assignEmployeeToOrder_allValid_callsPersistencePort(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PENDING, null,
                                new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573107654321", "www.logo.com",
                                               2L), List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish",
                                                                                                    new Category(1L,
                                                                                                                 "Categoría",
                                                                                                                 "Descripción"),
                                                                                                    "Descripción", 10000,
                                                                                                    new Restaurant(1L,
                                                                                                                   "Restaurant",
                                                                                                                   "123456789",
                                                                                                                   "Calle 123",
                                                                                                                   "+573107654321",
                                                                                                                   "www.logo.com",
                                                                                                                   2L),
                                                                                                    "www.image.com", true),
                                                                          2)), 20000, null);
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        
        orderUseCase.assignEmployeeToOrder(authHeader, 1L);
        verify(orderPersistencePort, times(1)).updateOrder(order);
    }
    
    @Test
    void assignEmployeeToOrder_invalidOrder_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(), OrderStateEnum.PENDING, null,
                                new Restaurant(2L, "Restaurant", "123456789", "Calle 123", "+573107654321", "www.logo.com",
                                               2L), List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish",
                                                                                                    new Category(1L,
                                                                                                                 "Categoría",
                                                                                                                 "Descripción"),
                                                                                                    "Descripción", 10000,
                                                                                                    new Restaurant(1L,
                                                                                                                   "Restaurant",
                                                                                                                   "123456789",
                                                                                                                   "Calle 123",
                                                                                                                   "+573107654321",
                                                                                                                   "www.logo.com",
                                                                                                                   2L),
                                                                                                    "www.image.com", true),
                                                                          2)), 20000, null);
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        assertThrows(EmployeeInvalidOperationException.class, () -> orderUseCase.assignEmployeeToOrder(authHeader, 1L));
    }
    
    @Test
    void setOrderReady_allValid_callsPersistencePort_and_messengerMSClientPort(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PREPARING, 3L,
                                new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573107654321", "www.logo.com",
                                               2L), List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish",
                                                                                                    new Category(1L,
                                                                                                                 "Categoría",
                                                                                                                 "Descripción"),
                                                                                                    "Descripción", 10000,
                                                                                                    new Restaurant(1L,
                                                                                                                   "Restaurant",
                                                                                                                   "123456789",
                                                                                                                   "Calle 123",
                                                                                                                   "+573107654321",
                                                                                                                   "www.logo.com",
                                                                                                                   2L),
                                                                                                    "www.image.com", true),
                                                                          2)), 20000, null);
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(orderUtils.generateOrderPIN()).thenReturn("1234");
        when(messengerMSClientPort.sendOrderReadyMessage("+573107654321", "+573101234567", "Restaurant", "1234"))
                .thenReturn(true);
        orderUseCase.setOrderReady(authHeader, 1L);
        verify(orderPersistencePort, times(1)).updateOrder(order);
        verify(messengerMSClientPort, times(1)).sendOrderReadyMessage("+573107654321", "+573101234567", "Restaurant", "1234");
    }
    
    @Test
    void setOrderReady_invalidOrder_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PREPARING, 3L,
                                new Restaurant(2L, "Restaurant", "123456789", "Calle 123", "+573107654321", "www.logo.com",
                                               2L), List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish",
                                                                                                    new Category(1L,
                                                                                                                 "Categoría",
                                                                                                                 "Descripción"),
                                                                                                    "Descripción", 10000,
                                                                                                    new Restaurant(1L,
                                                                                                                   "Restaurant",
                                                                                                                   "123456789",
                                                                                                                   "Calle 123",
                                                                                                                   "+573107654321",
                                                                                                                   "www.logo.com",
                                                                                                                   2L),
                                                                                                    "www.image.com", true),
                                                                          2)), 20000, null);
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        assertThrows(EmployeeInvalidOperationException.class, () -> orderUseCase.setOrderReady(authHeader, 1L));
    }
    
    @Test
    void setOrderReady_sendOrderReadyMessageFails_throwsException(){
        String authHeader = "validHeader";
        String validToken = "validToken";
        String requestUserMail = "validRequestUserMail";
        when(jwtServicePort.getTokenFromHeader(authHeader)).thenReturn(validToken);
        when(jwtServicePort.getMailFromToken(validToken)).thenReturn(requestUserMail);
        when(userMSClientPort.getUserByMail(authHeader, requestUserMail))
                .thenReturn(new User(3L, "John", "Doe","123456789","+573101234567",
                                     LocalDate.of(2000, 1, 1),"employee@mail.com", "password",
                                     new Role(3L, "ROLE_EMPLEADO", "Empleado")));
        when(restaurantEmployeeServicePort.validateEmployeeExistsInternal(3L)).thenReturn(true);
        when(restaurantEmployeeServicePort.getRestaurantId(3L)).thenReturn(1L);
        Restaurant restaurant = new Restaurant(1L, "Restaurant", "123456789", "Calle 123",
                                               "+573101234567", "www.logo.com", 2L);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurant);
        Order order = new Order(1L, 4L, "+573107654321", LocalDateTime.now(), LocalDateTime.now(),
                                OrderStateEnum.PREPARING, 3L,
                                new Restaurant(1L, "Restaurant", "123456789", "Calle 123", "+573107654321", "www.logo.com",
                                               2L), List.of(new OrderDish(1L, new Order(), new Dish(1L, "Dish",
                                                                                                    new Category(1L,
                                                                                                                 "Categoría",
                                                                                                                 "Descripción"),
                                                                                                    "Descripción", 10000,
                                                                                                    new Restaurant(1L,
                                                                                                                   "Restaurant",
                                                                                                                   "123456789",
                                                                                                                   "Calle 123",
                                                                                                                   "+573107654321",
                                                                                                                   "www.logo.com",
                                                                                                                   2L),
                                                                                                    "www.image.com", true),
                                                                          2)), 20000, null);
        when(orderPersistencePort.getOrderById(1L)).thenReturn(order);
        when(orderUtils.generateOrderPIN()).thenReturn("1234");
        when(messengerMSClientPort.sendOrderReadyMessage("+573107654321", "+573101234567", "Restaurant", "1234"))
                .thenReturn(false);
        assertThrows(MessageNotSentException.class, () -> orderUseCase.setOrderReady(authHeader, 1L));
    }
    
    
}
