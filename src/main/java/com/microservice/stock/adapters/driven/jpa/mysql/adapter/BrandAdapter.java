package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageNumberException;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageSizeException;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.CustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;


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
    public CustomPage<Brand> getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        if (pageNumber<0) {
            throw new NegativePageNumberException();
        }
        if (pageSize<=0) {
            throw new NegativePageSizeException();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortBy);
        Page<BrandEntity> page = brandRepository.findAll(pageRequest);

        List<Brand> brands= brandEntityMapper.toModelList(page);
        return new CustomPage<>(brands, pageNumber, pageSize, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

}
