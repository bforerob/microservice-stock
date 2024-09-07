package com.microservice.stock.adapters.driven.jpa.mysql.mapper;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {

    Brand toModel(BrandEntity brandEntity);
    BrandEntity toEntity(Brand brand);
    List<Brand> toModelList(Page<BrandEntity> brandEntities);


}
