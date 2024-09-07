package com.microservice.stock.adapters.driving.http.controller;


import com.microservice.stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.microservice.stock.adapters.driving.http.dto.response.BrandResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IBrandRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IBrandResponseMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
