package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IUserMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.domain.model.User;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IUserFeignClient;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.mapper.IUserMSClientResponseMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFeignClientAdapter implements IUserMSClientPort {
    
    private final IUserFeignClient userFeignClient;
    private final IUserMSClientResponseMapper userMSClientResponseMapper;
    
    @Override
    public User getOwnerById(Long id) {
        return userMSClientResponseMapper.ownerResponseDtoToUser(userFeignClient.getOwnerById(id));
    }
}
