package com.microservice.stock.configuration;

public class Constants {

    private Constants() { throw new IllegalStateException("Utility class"); }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String LENGTH_EXCEPTION_MESSAGE = "%s length out of range";
    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category you want to create already exists";


}
