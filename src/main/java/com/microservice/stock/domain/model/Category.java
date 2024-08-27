package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.EmptyFieldException;
import com.microservice.stock.domain.exception.LengthFieldException;
import com.microservice.stock.domain.util.DomainConstants;

import java.util.Objects;

public class Category {

    private final Long id;
    private final String name;
    private final String description;

    public Category(Long id, String name, String description) {

        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        if (name.length() > DomainConstants.MAX_CHARACTERS_CATEGORY_NAME) {
            throw new LengthFieldException(DomainConstants.Field.NAME.toString());
        }
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }
        if (description.length() > DomainConstants.MAX_CHARACTERS_CATEGORY_DESCRIPTION) {
            throw new LengthFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        this.id = id;
        this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = Objects.requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);

    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

}
