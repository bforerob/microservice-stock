package com.microservice.stock.configuration;

import com.microservice.stock.domain.util.DomainConstants;

public class Constants {

    private Constants() { throw new IllegalStateException("Utility class"); }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s cannot be empty.";
    public static final String NULL_FIELD_EXCEPTION_MESSAGE = "Field %s cannot be null.";
    public static final String LENGTH_EXCEPTION_MESSAGE = "%s length out of range.";
    public static final String ALREADY_EXISTS_EXCEPTION_MESSAGE = "The %s you want to create already exists.";
    public static final String NEGATIVE_PAGE_NUMBER_EXCEPTION_MESSAGE = "Page number cannot be negative.";
    public static final String NEGATIVE_PAGE_SIZE_EXCEPTION_MESSAGE = "Page size cannot be negative..";
    public static final String INVALID_SORT_DIRECTION_EXCEPTION_MESSAGE = "%s is not a valid sort direction.";
    public static final String INVALID_SORT_PARAMETER_EXCEPTION_MESSAGE = "%s is not a valid sort parameter.";
    public static final String ARTICLE_CATEGORIES_NUMBER_EXCEPTION = "An article cannot have less than "+ DomainConstants.MIN_ARTICLE_CATEGORIES_NUMBER + " categories and more than " + DomainConstants.MAX_ARTICLE_CATEGORIES_NUMBER + " categories.";
    public static final String DUPLICATED_ARTICLE_CATEGORIES_EXCEPTION = "An article cannot have duplicated categories.";
    public static final String NOT_FOUND_EXCEPTION = "%s does not exist.";
    public static final String NEGATIVE_FIELD_EXCEPTION = "%s cannot be negative.";
}
