package com.microservice.stock.configuration;

public class Constants {

    private Constants() { throw new IllegalStateException("Utility class"); }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String NULL_FIELD_EXCEPTION_MESSAGE = "Field %s can not be null";
    public static final String LENGTH_EXCEPTION_MESSAGE = "%s length out of range";
    public static final String ALREADY_EXISTS_EXCEPTION_MESSAGE = "The %s you want to create already exists";
    public static final String INVALID_PAGE_NUMBER_EXCEPTION_MESSAGE = "Page number %s does not exist.";
    public static final String INVALID_PAGE_SIZE_EXCEPTION_MESSAGE = "%s  is not a valid  page size.";
    public static final String INVALID_SORT_DIRECTION_EXCEPTION_MESSAGE = "%s is not a valid sort direction.";
    public static final String INVALID_SORT_PARAMETER_EXCEPTION_MESSAGE = "%s is not a valid sort parameter.";


}
