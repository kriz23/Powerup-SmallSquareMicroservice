package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.OrderTraceRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderTraceRequestMapper {
    OrderTraceRequestDto orderTraceToOrderTraceRequestDto(OrderTrace orderTrace);
}
