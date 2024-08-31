package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Category;

public interface ICategoryPersistencePort {

    Category addCategory(Category category);
    Boolean existsByName(String name);


}
