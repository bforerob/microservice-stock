package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.ICategoryResponseMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerAdapterTest {

    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    @Mock
    private ICategoryResponseMapper categoryResponseMapper;
    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    @Test
    @DisplayName("Given a correct AddCategoryRequest, rest controller should return CREATED status and categoryResponse")
    void When_AddCategoryRequestInformationIsCorrect_Expect_categoryResponseShouldBeAddedSuccessfully(){

        AddCategoryRequest request = new AddCategoryRequest("Electronics", "Electronic gadgets");
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Electronics", "Electronic gadgets");

        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);
        when(categoryServicePort.addCategory(category)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        ResponseEntity<CategoryResponse> responseEntity = categoryRestControllerAdapter.addCategory(request);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "Status code was wrong"),
                () -> assertEquals(categoryResponse, responseEntity.getBody(), "Category was saved incorrectly")
        );

    }

}