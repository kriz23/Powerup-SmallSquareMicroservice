package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    @Mapping(source = "restaurant.id", target = "restaurantEntity.id")
    OrderEntity orderToOrderEntity(Order order);
    
    @Mapping(source = "restaurantEntity.id", target = "restaurant.id")
    Order orderEntityToOrder(OrderEntity orderEntity);
}
