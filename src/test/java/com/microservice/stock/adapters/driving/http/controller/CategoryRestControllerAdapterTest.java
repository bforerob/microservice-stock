package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.ICategoryRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.ICategoryResponseMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryRestControllerAdapter.class)
class CategoryRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryServicePort categoryServicePort;

    @MockBean
    private ICategoryRequestMapper categoryRequestMapper;

    @MockBean
    private ICategoryResponseMapper categoryResponseMapper;

    @Test
    @DisplayName("Given a category, should return created status and the created category")
    void When_CategoryIsCorrect_Expect_ReturnCreatedStatus() throws Exception {

        Category category = new Category(1L, "Category Name", "Category Description");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Category Name", "Category Description");


        when(categoryRequestMapper.addRequestToCategory(any(AddCategoryRequest.class))).thenReturn(category);
        when(categoryServicePort.addCategory(any(Category.class))).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(any(Category.class))).thenReturn(categoryResponse);

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Category Name\",\"description\":\"Category Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Category Name"))
                .andExpect(jsonPath("$.description").value("Category Description"));

        verify(categoryServicePort).addCategory(any(Category.class));
        verify(categoryResponseMapper).toCategoryResponse(any(Category.class));
    }

    @Test
    @DisplayName("Given correct parameters, should return ok status and custom page whit paginated categories")
    void When_ParametersAreCorrect_Expect_ReturnOkStatus() throws Exception {

        Category category1 = new Category(1L, "Category A", "Description A");
        Category category2 = new Category(2L, "Category B", "Description B");
        List<Category> categories = Arrays.asList(category1, category2);

        CustomPage<Category> categoryCustomPage = new CustomPage<>(categories, 0, 2, 2L, 1);

        CategoryResponse categoryResponse1 = new CategoryResponse(1L, "Category A", "Description A");
        CategoryResponse categoryResponse2 = new CategoryResponse(2L, "Category B", "Description B");
        List<CategoryResponse> categoryResponses = Arrays.asList(categoryResponse1, categoryResponse2);

        when(categoryServicePort.getAllCategories(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(categoryCustomPage);
        when(categoryResponseMapper.toCategoryResponsesList(anyList())).thenReturn(categoryResponses);

        mockMvc.perform(get("/category/")
                        .param("pageNumber", "0")
                        .param("pageSize", "2")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Category A"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].name").value("Category B"))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(2))
                .andExpect(jsonPath("$.totalElements").value(2L))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(categoryServicePort).getAllCategories(anyInt(), anyInt(), anyString(), anyString());
        verify(categoryResponseMapper).toCategoryResponsesList(anyList());
    }


}
