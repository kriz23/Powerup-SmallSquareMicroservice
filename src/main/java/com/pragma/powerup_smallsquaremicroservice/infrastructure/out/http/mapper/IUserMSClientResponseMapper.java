package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper;

import com.pragma.powerup_smallsquaremicroservice.application.dto.response.UserMSResponseDto;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy =
        ReportingPolicy.IGNORE)
public interface IUserMSClientResponseMapper {
    User userMSResponseDtoToUser(UserMSResponseDto userMSResponseDto);
}
