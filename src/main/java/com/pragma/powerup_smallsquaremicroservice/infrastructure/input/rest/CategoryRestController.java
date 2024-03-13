package com.pragma.powerup_smallsquaremicroservice.infrastructure.input.rest;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.CategoryResponseDto;
import com.pragma.powerup_smallsquaremicroservice.application.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Category Rest Controller", description = "Rest controller for category operations")
@RestController
@RequestMapping("/api/v1/smallsquare")
@RequiredArgsConstructor
public class CategoryRestController {
    private final ICategoryHandler categoryHandler;
    
    @Operation(summary = "Get all categories")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "All categories returned", content =
    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation =
            CategoryResponseDto.class)))), @ApiResponse(responseCode = "404", description = "No data found", content =
    @Content)})
    @GetMapping("/admins/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(categoryHandler.getAllCategories());
    }
    
}
