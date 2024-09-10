package com.microservice.stock.adapters.driving.http.mapper.request;

import com.microservice.stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.microservice.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {

    @Mapping(target = "id", ignore = true)
    Brand addBrandRequestToBrand(AddBrandRequest addBrandRequest);

}
