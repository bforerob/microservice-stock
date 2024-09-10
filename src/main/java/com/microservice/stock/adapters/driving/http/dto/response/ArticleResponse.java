package com.microservice.stock.adapters.driving.http.dto.response;

import com.microservice.stock.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleResponse {

    private final Long id;
    private final List<Category> categories;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;

}
