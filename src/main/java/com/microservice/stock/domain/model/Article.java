package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import com.microservice.stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Article {

    private Long id;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;
    private Brand brand;
    private List<Category> categories;


    public Article(Long id,  String name, String description, Long quantity, BigDecimal price, Brand brand, List<Category> categories) {
        this.id = id;
        this.name = Objects.requireNonNull(name, () -> {
            throw new NullFieldException(DomainConstants.Field.NAME.toString());
        });
        this.description = Objects.requireNonNull(description, () -> {
            throw new NullFieldException(DomainConstants.Field.DESCRIPTION.toString());
        });
        this.quantity = Objects.requireNonNull(quantity, () -> {
            throw new NullFieldException(DomainConstants.Field.QUANTITY.toString());
        });
        this.price = Objects.requireNonNull(price, () -> {
            throw new NullFieldException(DomainConstants.Field.PRICE.toString());
        });
        this.brand = brand;
        this.categories = categories;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Brand getBrand() {return brand;}

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
