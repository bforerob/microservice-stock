package com.microservice.stock.domain.util;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field{
        CATEGORY,
        BRAND,
        ARTICLE,
        NAME,
        DESCRIPTION,
        QUANTITY,
        PRICE,
        PAGE_NUMBER,
        PAGE_SIZE,
        CATEGORIES,
    }

    public static final Integer MAX_CHARACTERS_CATEGORY_NAME = 50;
    public static final Integer MAX_CHARACTERS_CATEGORY_DESCRIPTION = 90;
    public static final Integer MAX_CHARACTERS_BRAND_NAME = 50;
    public static final Integer MAX_CHARACTERS_BRAND_DESCRIPTION = 120;
    public static final Integer MIN_ARTICLE_CATEGORIES_NUMBER = 1;
    public static final Integer MAX_ARTICLE_CATEGORIES_NUMBER = 3;
    public static final String ASCENDENT_SORT_DIRECTION = "asc";
    public static final String DESCENDENT_SORT_DIRECTION = "desc";
    public static final String GET_ALL_CATEGORIES_SORT_PARAMETER = "name";
    public static final String GET_ARTICLES_SORT_PARAMETER = "name";


}
