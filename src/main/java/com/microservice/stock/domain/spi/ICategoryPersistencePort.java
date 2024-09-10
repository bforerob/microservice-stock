package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.CustomPage;

import java.util.List;

public interface ICategoryPersistencePort {

    Category addCategory(Category category);
    CustomPage<Category> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Boolean existsByName(String name);
    List<Category> findCategoriesByNames(List<String> names);

}
