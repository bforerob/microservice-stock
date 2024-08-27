package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Category;

public interface ICategoryPersistencePort {

    void saveCategory(Category category);

}
