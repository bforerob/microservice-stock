package com.microservice.stock.adapters.driven.jpa.mysql.mapper;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {

    Category categoryEntityToCategory(CategoryEntity categoryEntity);
    CategoryEntity categoryToCategoryEntity(Category category);
    List<Category> categoryEntityPageToModelList(Page<CategoryEntity> categoryEntities);

    List<Category> categoryEntityListToCategoryList(List<CategoryEntity> categoryEntities);
    List<CategoryEntity> categoryListToCategoryEntityList(List<Category> categories);
}
