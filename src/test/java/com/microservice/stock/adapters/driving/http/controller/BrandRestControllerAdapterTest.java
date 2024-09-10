package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.microservice.stock.adapters.driving.http.dto.response.BrandResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IBrandRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IBrandResponseMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandRestControllerAdapter.class)
class BrandRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandServicePort brandServicePort;

    @MockBean
    private IBrandRequestMapper brandRequestMapper;

    @MockBean
    private IBrandResponseMapper brandResponseMapper;

    @Test
    @DisplayName("Given a brand, should return created status and the created brand")
    void When_BrandIsCorrect_Expect_ReturnCreatedStatus() throws Exception {

        Brand brand = new Brand(1L, "Brand Name", "Brand Description");
        BrandResponse brandResponse = new BrandResponse(1L, "Brand Name", "Brand Description");


        when(brandRequestMapper.addBrandRequestToBrand(any(AddBrandRequest.class))).thenReturn(brand);
        when(brandServicePort.addBrand(any(Brand.class))).thenReturn(brand);
        when(brandResponseMapper.brandToBrandResponse(any(Brand.class))).thenReturn(brandResponse);

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Brand Name\",\"description\":\"Brand Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Brand Name"))
                .andExpect(jsonPath("$.description").value("Brand Description"));

        verify(brandServicePort).addBrand(any(Brand.class));
        verify(brandResponseMapper).brandToBrandResponse(any(Brand.class));
    }

    @Test
    @DisplayName("Given correct parameters, should return ok status and custom page with paginated brands")
    void When_ParametersAreCorrect_Expect_ReturnOkStatus_Brand() throws Exception {

        Brand brand1 = new Brand(1L, "Brand A", "Description A");
        Brand brand2 = new Brand(2L, "Brand B", "Description B");
        List<Brand> brands = Arrays.asList(brand1, brand2);

        CustomPage<Brand> brandCustomPage = new CustomPage<>(brands, 0, 2, 2L, 1);

        BrandResponse brandResponse1 = new BrandResponse(1L, "Brand A", "Description A");
        BrandResponse brandResponse2 = new BrandResponse(2L, "Brand B", "Description B");
        List<BrandResponse> brandResponses = Arrays.asList(brandResponse1, brandResponse2);

        when(brandServicePort.getAllBrands(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(brandCustomPage);
        when(brandResponseMapper.brandListToBrandResponsesList(anyList())).thenReturn(brandResponses);

        mockMvc.perform(get("/brand/")
                        .param("pageNumber", "0")
                        .param("pageSize", "2")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Brand A"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].name").value("Brand B"))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(2))
                .andExpect(jsonPath("$.totalElements").value(2L))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(brandServicePort).getAllBrands(anyInt(), anyInt(), anyString(), anyString());
        verify(brandResponseMapper).brandListToBrandResponsesList(anyList());
    }


}