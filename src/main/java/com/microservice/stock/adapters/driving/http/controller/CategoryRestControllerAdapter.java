package com.microservice.stock.adapters.driving.http.controller;


import com.microservice.stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.ICategoryResponseMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @PostMapping("/")
    public ResponseEntity<AddCategoryRequest> addSupplier(@RequestBody AddCategoryRequest request) {
        Category category = categoryRequestMapper.addRequestToCategory(request);
        categoryServicePort.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

}
