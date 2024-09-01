package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.exception.AlreadyExistsException;
import com.microservice.stock.domain.exception.EmptyFieldException;
import com.microservice.stock.domain.exception.LengthFieldException;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category addCategory(Category category) {

        if (category.getName().trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        if (category.getName().length() > DomainConstants.MAX_CHARACTERS_CATEGORY_NAME) {
            throw new LengthFieldException(DomainConstants.Field.NAME.toString());
        }
        if(Boolean.TRUE.equals(categoryPersistencePort.existsByName(category.getName()))) {
            throw new AlreadyExistsException(DomainConstants.Field.CATEGORY.toString());
        }

        if (category.getDescription().trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }
        if (category.getDescription().length() > DomainConstants.MAX_CHARACTERS_CATEGORY_DESCRIPTION) {
            throw new LengthFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        return categoryPersistencePort.addCategory(category);

    }

}
