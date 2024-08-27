package com.microservice.stock.adapters.driving.http.mapper.request;

import com.microservice.stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category addRequestToCategory(AddCategoryRequest addCategoryRequest);

}
