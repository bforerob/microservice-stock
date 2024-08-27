package com.microservice.stock.domain.util;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field{
        NAME,
        DESCRIPTION
    }

    public static final Integer MAX_CHARACTERS_CATEGORY_NAME = 5;
    public static final Integer MAX_CHARACTERS_CATEGORY_DESCRIPTION = 9;
    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";

}
