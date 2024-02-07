package com.pragma.powerup_smallsquaremicroservice.application.handler;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.ObjectRequestDto;
import com.pragma.powerup_smallsquaremicroservice.application.dto.response.ObjectResponseDto;

import java.util.List;

public interface IObjectHandler {

    void saveObject(ObjectRequestDto objectRequestDto);

    List<ObjectResponseDto> getAllObjects();
}