package com.microservice.stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {

    private String name;
    private String description;

}
