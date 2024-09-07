package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.CustomPage;

public interface IBrandPersistencePort {

    Brand addBrand(Brand brand);
    CustomPage<Brand> getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Boolean existsByName(String name);


}
