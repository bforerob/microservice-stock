package com.microservice.stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String name;
    private String description;
    private Long quantity;
    private BigDecimal price;
    private String brand;
    private List<String> categories;

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
