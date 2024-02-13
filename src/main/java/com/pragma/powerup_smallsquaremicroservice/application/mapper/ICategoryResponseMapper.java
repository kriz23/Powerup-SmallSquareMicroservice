package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.CategoryResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {
    List<CategoryResponseDto> categoryToCategoryResponseDtoList(List<Category> categoryList);
}
