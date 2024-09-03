package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {

    Category addCategory(Category category);
    List<Category> getAllCategories();

}
