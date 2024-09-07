package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;


    @Override
    public Brand addBrand(Brand brand) {
        BrandEntity savedEntity = brandRepository.save(brandEntityMapper.toEntity(brand));
        return brandEntityMapper.toModel(savedEntity);
    }

    @Override
    public Boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

}
