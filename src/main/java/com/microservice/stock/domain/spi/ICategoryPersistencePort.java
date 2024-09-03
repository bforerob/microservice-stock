package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {

    Category addCategory(Category category);
    List<Category> getAllCategories();
    Boolean existsByName(String name);


}
