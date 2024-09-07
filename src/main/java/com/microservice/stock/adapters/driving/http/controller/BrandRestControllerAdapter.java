package com.microservice.stock.adapters.driving.http.controller;


import com.microservice.stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.microservice.stock.adapters.driving.http.dto.response.BrandResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IBrandRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IBrandResponseMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;


    @Operation(summary = "Add a new brand")
    @ApiResponse(responseCode = "201", description = "brand created successfully")
    @ApiResponse(responseCode = "400", description = "Wrong brand information")
    @PostMapping("/")
    public ResponseEntity<BrandResponse> addBrand(@RequestBody AddBrandRequest request) {
        Brand createdBrand = brandServicePort.addBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(brandResponseMapper.toBrandResponse(createdBrand));
    }

    @Operation(summary = "Get all brands paginated and sorted by name")
    @ApiResponse(responseCode = "200", description = "Brands obtained successfully")
    @ApiResponse(responseCode = "400", description = "Wrong parameters")
    @GetMapping("/")
    public ResponseEntity<CustomPage<BrandResponse>> getAllBrands(
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize,
            @RequestParam String sortBy, @RequestParam String sortDirection) {

        CustomPage<Brand> brandCustomPage = brandServicePort.getAllBrands(pageNumber, pageSize, sortBy, sortDirection);
        List<BrandResponse> brandResponses = brandResponseMapper.toBrandResponsesList(brandCustomPage.getContent());

        return ResponseEntity.ok(new CustomPage<>(brandResponses, brandCustomPage.getPageNumber(),
                brandCustomPage.getPageSize(), brandCustomPage.getTotalElements(), brandCustomPage.getTotalPages()));
    }

}
