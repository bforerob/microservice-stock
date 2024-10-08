package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.exception.*;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.CustomPage;
import com.microservice.stock.domain.util.DomainConstants;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand addBrand(Brand brand) {

        if (brand.getName().trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        if (brand.getName().length() > DomainConstants.MAX_CHARACTERS_BRAND_NAME) {
            throw new LengthFieldException(DomainConstants.Field.NAME.toString());
        }
        if(Boolean.TRUE.equals(brandPersistencePort.existsByName(brand.getName()))) {
            throw new AlreadyExistsException(DomainConstants.Field.BRAND.toString());
        }

        if (brand.getDescription().trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }
        if (brand.getDescription().length() > DomainConstants.MAX_CHARACTERS_BRAND_DESCRIPTION) {
            throw new LengthFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        return brandPersistencePort.addBrand(brand);
    }

    @Override
    public CustomPage<Brand> getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        if (!sortDirection.equalsIgnoreCase(DomainConstants.DESCENDENT_SORT_DIRECTION) &&
                !sortDirection.equalsIgnoreCase(DomainConstants.ASCENDENT_SORT_DIRECTION)) {
            throw new InvalidSortDirectionException(sortDirection);
        }

        if (!sortBy.equalsIgnoreCase(DomainConstants.GET_ALL_CATEGORIES_SORT_PARAMETER)){
            throw new InvalidSortParameterException(sortBy);
        }

        return brandPersistencePort.getAllBrands(pageNumber, pageSize, sortBy, sortDirection);

    }
}
