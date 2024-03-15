package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IOrderTraceResponseMapper {
    OrderTraceResponseDto orderTraceToOrderTraceResponseDto(OrderTrace orderTrace);
    
    List<OrderTraceResponseDto> orderTraceListToOrderTraceResponseDtoList(List<OrderTrace> orderTraceList);
}
