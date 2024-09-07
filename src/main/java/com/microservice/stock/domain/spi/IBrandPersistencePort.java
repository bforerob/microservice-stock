package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Brand;

public interface IBrandPersistencePort {

    Brand addBrand(Brand brand);
    Boolean existsByName(String name);


}
