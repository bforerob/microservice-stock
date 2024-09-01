package com.microservice.stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {

    private final String name;
    private final String description;

}
