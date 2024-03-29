package com.pragma.powerup_smallsquaremicroservice.infrastructure.input.rest;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderDurationResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IOrderHandler;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.ClientOrderInvalidException;
import com.pragma.powerup_smallsquaremicroservice.domain.utils.OrderStateEnum;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.PaginationInvalidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Order Rest Controller", description = "Rest controller for order operations")
@RestController
@RequestMapping("/api/v1/smallsquare")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler orderHandler;
    
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
        @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content = @Content)})
    @PostMapping("/clients/restaurants/{id}/orders")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> createOrder(@Parameter(description = "Restaurant's id") @PathVariable Long id,
                                            @RequestBody OrderRequestDto orderRequestDto,
                                            HttpServletRequest request){
        orderRequestDto.setIdRestaurant(id);
        if (orderRequestDto.getOrderDishes().isEmpty()) {
            throw new ClientOrderInvalidException();
        }
        orderHandler.createOrder(orderRequestDto, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get orders from restaurant by state pageable")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Orders returned", content =
    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =OrderResponseDto.class)))),
        @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content = @Content),
        @ApiResponse(responseCode = "404", description = "No dishes found", content = @Content)})
    @GetMapping("/employees/orders")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Page<OrderResponseDto>> getRestaurantOrders(@RequestParam(defaultValue = "")OrderStateEnum state,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      HttpServletRequest request){
        if (page < 0 || size < 1){
            throw new PaginationInvalidException();
        }
        return ResponseEntity.ok(orderHandler.getOrdersFromRestaurantByStatePageable(state, page, size, request));
    }
    
    @Operation(summary = "Assign employee to order")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Order updated", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content =
            @Content), @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/employees/orders/assign/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Void> assignEmployeeToOrder(@Parameter(description = "Order id") @PathVariable Long id,
                                                     HttpServletRequest request){
        orderHandler.assignEmployeeToOrder(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Update order to ready")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Order updated", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content =
            @Content), @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/employees/orders/ready/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Void> setOrderReady(@Parameter(description = "Order id") @PathVariable Long id,
                                             HttpServletRequest request){
        orderHandler.setOrderReady(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Update order to delivered | Deliver an order")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Order updated", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content =
            @Content), @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/employees/orders/deliver/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Void> setOrderDelivered(@Parameter(description = "Order id") @PathVariable Long id,
                                                  @RequestParam String orderPIN,
                                                  HttpServletRequest request){
        orderHandler.setOrderDelivered(id, orderPIN, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Get client pending orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "All client pending orders returned",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =
            OrderResponseDto.class)))), @ApiResponse(responseCode = "403", description = "User not allowed for this " +
            "operation", content = @Content), @ApiResponse(responseCode = "404", description = "No data found",
            content = @Content)})
    @GetMapping("/clients/orders/pending")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<OrderResponseDto>> getClientPendingOrders(HttpServletRequest request){
        return ResponseEntity.ok(orderHandler.getClientPendingOrders(request));
    }
    
    @Operation(summary = "Cancel order")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Order updated", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content =
            @Content), @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/clients/orders/cancel/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> setOrderCancelled(@Parameter(description = "Order id") @PathVariable Long id,
                                                 HttpServletRequest request){
        orderHandler.cancelOrder(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Get Order trace by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order trace returned",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =
                    OrderTraceResponseDto.class)))), @ApiResponse(responseCode = "403", description = "User not allowed for " +
            "this operation", content = @Content), @ApiResponse(responseCode = "404", description = "No data found",
            content = @Content)})
    @GetMapping("/clients/orders/trace/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<OrderTraceResponseDto>> getOrderTracesByIdOrder(@Parameter(description = "Order id") @PathVariable Long id,
                                                                               HttpServletRequest request){
        return ResponseEntity.ok(orderHandler.getOrderTracesByIdOrder(id, request));
    }
    
    @Operation(summary = "Get Order duration by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order's duration returned ", content =
    @Content), @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content =
            @Content), @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @GetMapping("/owners/orders/duration/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<OrderDurationResponseDto> getOrderDurationByIdOrder(@Parameter(description = "Order id") @PathVariable Long id,
                                                                              HttpServletRequest request){
        return ResponseEntity.ok(orderHandler.getOrderDurationByIdOrder(id, request));
    }
    
}
