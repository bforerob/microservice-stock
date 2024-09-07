package com.microservice.stock.adapters.driving.http.controller;


import com.microservice.stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.ICategoryResponseMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.model.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Operation(summary = "Add a new category")
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    @ApiResponse(responseCode = "400", description = "Wrong category information")
    @PostMapping("/")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody AddCategoryRequest request) {
        Category createdCategory = categoryServicePort.addCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseMapper.toCategoryResponse(createdCategory));
    }
    
    @Operation(summary = "Get all categories paginated and sorted by name")
    @ApiResponse(responseCode = "200", description = "Categories obtained successfully")
    @ApiResponse(responseCode = "400", description = "Wrong parameters")
    @GetMapping("/")
    public ResponseEntity<CustomPage<CategoryResponse>> getAllCategories(
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize,
            @RequestParam String sortBy, @RequestParam String sortDirection) {

        CustomPage<Category> categoryCustomPage = categoryServicePort.getAllCategories(pageNumber, pageSize, sortBy, sortDirection);
        List<CategoryResponse> categoryResponses = categoryResponseMapper.toCategoryResponsesList(categoryCustomPage.getContent());

        return ResponseEntity.ok(new CustomPage<>(categoryResponses, categoryCustomPage.getPageNumber(),
                categoryCustomPage.getPageSize(), categoryCustomPage.getTotalElements(), categoryCustomPage.getTotalPages()));
    }

}
