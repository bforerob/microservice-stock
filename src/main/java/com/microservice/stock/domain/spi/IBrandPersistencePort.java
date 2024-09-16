package com.microservice.stock.domain.spi;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.CustomPage;

import java.util.Optional;

public interface IBrandPersistencePort {

    Brand addBrand(Brand brand);
    CustomPage<Brand> getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Boolean existsByName(String name);
    Optional<Brand> findByName(String name);

}
