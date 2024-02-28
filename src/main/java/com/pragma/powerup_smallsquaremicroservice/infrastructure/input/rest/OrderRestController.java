package com.pragma.powerup_smallsquaremicroservice.infrastructure.input.rest;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IOrderHandler;
import com.pragma.powerup_smallsquaremicroservice.domain.exception.ClientOrderInvalidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Order Rest Controller", description = "Rest controller for order operations")
@RestController
@RequestMapping("/api/v1/smallsquare")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler orderHandler;
    
    @Operation(summary = "Create a new order")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content), @ApiResponse(responseCode = "403", description = "User not allowed for this operation", content = @Content)})
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
    
}
