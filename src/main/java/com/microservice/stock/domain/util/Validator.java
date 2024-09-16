package com.microservice.stock.domain.util;

import com.microservice.stock.domain.exception.InvalidSortDirectionException;
import com.microservice.stock.domain.exception.InvalidSortParameterException;
import com.microservice.stock.domain.exception.NegativeFieldException;

public class Validator {

    public static void validateCustomPage(Integer pageNumber, Integer pageSize, String sortBy, String defaultSortBy, String sortDirection) {
        if (pageNumber < 0) {
            throw new NegativeFieldException(DomainConstants.Field.PAGE_NUMBER.toString());
        }
        if (pageSize < 0) {
            throw new NegativeFieldException(DomainConstants.Field.PAGE_NUMBER.toString());
        }
        if (!sortDirection.equalsIgnoreCase(DomainConstants.DESCENDENT_SORT_DIRECTION) &&
                !sortDirection.equalsIgnoreCase(DomainConstants.ASCENDENT_SORT_DIRECTION)) {
            throw new InvalidSortDirectionException(sortDirection);
        }
        if (!sortBy.equals(defaultSortBy)) {
            throw new InvalidSortParameterException(sortBy);
        }


    }

}
