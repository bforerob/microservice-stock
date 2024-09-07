package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.CustomPage;

public interface IBrandServicePort {

    Brand addBrand(Brand brand);
    CustomPage<Brand> getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);


}
