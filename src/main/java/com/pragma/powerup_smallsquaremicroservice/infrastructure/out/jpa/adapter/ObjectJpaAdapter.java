package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.adapter;

import com.pragma.powerup_smallsquaremicroservice.domain.model.ObjectModel;
import com.pragma.powerup_smallsquaremicroservice.domain.spi.IObjectPersistencePort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.entity.ObjectEntity;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObjectJpaAdapter implements IObjectPersistencePort {

    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;


    @Override
    public ObjectModel saveObject(ObjectModel objectModel) {
        ObjectEntity objectEntity = objectRepository.save(objectEntityMapper.toEntity(objectModel));
        return objectEntityMapper.toObjectModel(objectEntity);
    }

    @Override
    public List<ObjectModel> getAllObjects() {
        List<ObjectEntity> entityList = objectRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return objectEntityMapper.toObjectModelList(entityList);
    }
}