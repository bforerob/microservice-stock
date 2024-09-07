package com.microservice.stock.adapters.driving.http.mapper.response;

import com.microservice.stock.adapters.driving.http.dto.response.BrandResponse;
import com.microservice.stock.domain.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponsesList(List<Brand> brands);

}
