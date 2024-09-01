package com.microservice.stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;


}

