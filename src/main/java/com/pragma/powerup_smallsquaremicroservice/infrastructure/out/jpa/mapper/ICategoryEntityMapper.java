package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Category;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    List<Category> categoryEntityListToCategoryList(List<CategoryEntity> categoryEntityList);
}
