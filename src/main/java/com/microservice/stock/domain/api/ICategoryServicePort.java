package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Category;

public interface ICategoryServicePort {

    Category addCategory(Category category);

}
