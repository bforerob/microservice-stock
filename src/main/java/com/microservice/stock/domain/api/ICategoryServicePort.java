package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.CustomPage;

public interface ICategoryServicePort {

    Category addCategory(Category category);
    CustomPage<Category> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

}
