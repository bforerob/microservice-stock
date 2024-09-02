package com.microservice.stock.domain.util;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field{
        CATEGORY,
        NAME,
        DESCRIPTION
    }

    public static final Integer MAX_CHARACTERS_CATEGORY_NAME = 50;
    public static final Integer MAX_CHARACTERS_CATEGORY_DESCRIPTION = 90;

}
