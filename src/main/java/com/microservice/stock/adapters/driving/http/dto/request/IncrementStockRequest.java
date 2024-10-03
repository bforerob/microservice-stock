package com.microservice.stock.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IncrementStockRequest {

    private final Long articleId;
    private final Integer quantity;

}
