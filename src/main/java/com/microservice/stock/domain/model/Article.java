package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import com.microservice.stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Article {

    private final Long id;
    private List<Category> categories;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;



    public Article(Long id, List<Category> categories, String name, String description, Long quantity, BigDecimal price) {
        this.id = id;
        this.categories = categories;
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
    }


    public Long getId() {
        return id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

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
}
