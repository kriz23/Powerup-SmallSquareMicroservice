package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper;

import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderDish;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {
    @Mapping(source = "order.id", target = "orderEntity.id")
    @Mapping(source = "dish.id", target = "dishEntity.id")
    OrderDishEntity orderDishToOrderDishEntity(OrderDish orderDish);
    
    @Mapping(source = "orderEntity.id", target = "order.id")
    @Mapping(source = "dishEntity.id", target = "dish.id")
    OrderDish orderDishEntityToOrderDish(OrderDishEntity orderDishEntity);
    
    List<OrderDishEntity> orderDishListToOrderDishEntityList(List<OrderDish> orderDishList);
    
    List<OrderDish> orderDishEntityListToOrderDishList(List<OrderDishEntity> orderDishEntityList);
}
