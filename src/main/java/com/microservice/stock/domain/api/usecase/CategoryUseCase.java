package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void addCategory(Category category) {
        categoryPersistencePort.saveCategory(category);
    }

}
