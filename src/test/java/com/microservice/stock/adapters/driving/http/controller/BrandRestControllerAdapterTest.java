package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.microservice.stock.adapters.driving.http.dto.response.BrandResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IBrandRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IBrandResponseMapper;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.model.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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


        when(brandRequestMapper.addRequestToBrand(any(AddBrandRequest.class))).thenReturn(brand);
        when(brandServicePort.addBrand(any(Brand.class))).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(any(Brand.class))).thenReturn(brandResponse);

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Brand Name\",\"description\":\"Brand Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Brand Name"))
                .andExpect(jsonPath("$.description").value("Brand Description"));

        verify(brandServicePort).addBrand(any(Brand.class));
        verify(brandResponseMapper).toBrandResponse(any(Brand.class));
    }

}