package com.pragma.powerup_smallsquaremicroservice.domain.usecase;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    
    @InjectMocks
    private CategoryUseCase categoryUseCase;
    
    @Test
    void getAllCategories_callsPersistencePort(){
        List<Category> expectedCategories = List.of(new Category(1L, "Categoría 1", "Descripción 1"),
                                                   new Category(2L, "Categoría 2", "Descripción 2"),
                                                   new Category(3L, "Categoría 3", "Descripción 3"),
                                                   new Category(4L, "Categoría 4", "Descripción 4"));
        when(categoryPersistencePort.getAllCategories()).thenReturn(expectedCategories);
        
        List<Category> actualCategories = categoryUseCase.getAllCategories();
        
        assertEquals(expectedCategories, actualCategories);
    }
    
    @Test
    void getAllCategories_returnEmptyList(){
        List<Category> expectedCategories = List.of();
        when(categoryPersistencePort.getAllCategories()).thenReturn(expectedCategories);
        
        List<Category> actualCategories = categoryUseCase.getAllCategories();
        
        assertTrue(actualCategories.isEmpty());
    }
}