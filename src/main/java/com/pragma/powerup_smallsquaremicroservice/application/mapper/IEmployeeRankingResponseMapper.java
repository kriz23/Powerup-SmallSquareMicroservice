package com.pragma.powerup_smallsquaremicroservice.application.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.EmployeeRankingResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.EmployeeRanking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IEmployeeRankingResponseMapper {
    EmployeeRankingResponseDto employeeRankingToEmployeeRankingResponseDto(EmployeeRanking employeeRanking);
    
    List<EmployeeRankingResponseDto> employeeRankingListToEmployeeRankingResponseDtoList(List<EmployeeRanking> employeeRankings);
}
