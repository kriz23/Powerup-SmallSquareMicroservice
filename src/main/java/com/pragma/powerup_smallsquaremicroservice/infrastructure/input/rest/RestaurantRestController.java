package com.pragma.powerup_smallsquaremicroservice.infrastructure.input.rest;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IRestaurantEmployeeHandler;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IRestaurantHandler;
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

@Tag(name = "Restaurant Rest Controller", description = "Rest controller for restaurant operations")
@RestController
@RequestMapping("/api/v1/smallsquare/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantHandler restaurantHandler;
    private final IRestaurantEmployeeHandler restaurantEmployeeHandler;
    
    @Operation(summary = "Create a new restaurant")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content)
            , @ApiResponse(responseCode = "409", description = "Restaurant already exists", content = @Content),
            @ApiResponse(responseCode = "403", description = "Owner not allowed for restaurant creation", content =
            @Content), @ApiResponse(responseCode = "404", description = "The owner you provided does not exists",
            content = @Content)})
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto, HttpServletRequest request) {
        restaurantHandler.createRestaurant(restaurantRequestDto, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @Operation(summary = "Validate restaurant ownership")
    @ApiResponse(responseCode = "200", description = "Ownership validation ready for further processing",content = @Content)
    @GetMapping("/verify-owner/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Boolean> verifyRestaurantOwnership(@Parameter(description = "Restaurant's id") @PathVariable Long id,
                                                             HttpServletRequest request){
        return ResponseEntity.ok(restaurantHandler.validateRestaurantOwnership(id, request));
    }
    
    @Operation(summary = "Assign employee to restaurant")
    @ApiResponse(responseCode = "200", description = "Attempt to assign an employee done", content = @Content)
    @PostMapping("/employee")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Boolean> assignEmployeeToRestaurant(@RequestParam("idEmployee") Long idEmployee,
                                                              @RequestParam("idRestaurant") Long idRestaurant,
                                                              HttpServletRequest request){
        return ResponseEntity.ok(restaurantEmployeeHandler.assignEmployeeToRestaurant(idEmployee, idRestaurant, request));
    }
}
