package com.pragma.powerup_smallsquaremicroservice.infrastructure.input.rest;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.DishResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.IDishHandler;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.DishAvailableStatusInvalidException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Dish Rest Controller", description = "Rest controller for dish operations")
@RestController
@RequestMapping("/api/v1/smallsquare")
@RequiredArgsConstructor
public class DishRestController {
    private final IDishHandler dishHandler;
    
    @Operation(summary = "Create a new dish")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish already exists", content = @Content)})
    @PostMapping("/owners/restaurants/{id}/dishes")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Void> createDish(@Parameter(description = "Restaurant id") @PathVariable Long id,
                                           @RequestBody DishRequestDto dishRequestDto,
                                           HttpServletRequest request) {
        dishRequestDto.setIdRestaurant(id);
        dishHandler.createDish(dishRequestDto, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get desired dish by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Desired owner returned", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = DishResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @GetMapping("/owners/dishes/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<DishResponseDto> getDish(@Parameter(description = "Dish id") @PathVariable Long id) {
        return ResponseEntity.ok(dishHandler.getDishById(id));
    }
    
    @Operation(summary = "Update desired dish")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Dish updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/owners/dishes/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Void> updateDish(@Parameter(description = "Dish id")
                                           @PathVariable Long id,
                                           @RequestBody DishUpdateRequestDto dishUpdateRequestDto,
                                           HttpServletRequest request) {
        dishHandler.updateDish(id, dishUpdateRequestDto, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Update desired dish status")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Dish status updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)})
    @PutMapping("/owners/dishes/enable-disable/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Void> updateDishStatus(@Parameter(description = "Dish id") @PathVariable Long id,
                                                 @Parameter(description = "Dish status") @RequestParam String dishAvailableStatus,
                                                 HttpServletRequest request) {
        if (dishAvailableStatus.equalsIgnoreCase("true") || dishAvailableStatus.equalsIgnoreCase("false")){
            boolean status = Boolean.parseBoolean(dishAvailableStatus);
            dishHandler.updateDishStatus(id, status, request);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new DishAvailableStatusInvalidException();
        }
    }
}
