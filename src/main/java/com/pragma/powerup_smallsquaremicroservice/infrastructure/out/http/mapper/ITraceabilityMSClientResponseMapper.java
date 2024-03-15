package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.OrderTraceMSResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.OrderTrace;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface ITraceabilityMSClientResponseMapper {
    OrderTrace traceabilityMSOrderTraceResponseDtoToOrderTrace(OrderTraceMSResponseDto orderTraceMSResponseDto);
    
    List<OrderTrace> traceabilityMSOrderTraceResponseDtoListToOrderTraceList(List<OrderTraceMSResponseDto> orderTraceMSResponseDtoList);
}
