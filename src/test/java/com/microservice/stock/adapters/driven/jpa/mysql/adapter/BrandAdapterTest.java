package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.microservice.stock.domain.model.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandAdapterTest {

    @Mock
    private IBrandRepository brandRepository;
    @Mock
    private IBrandEntityMapper brandEntityMapper;
    @InjectMocks
    private BrandAdapter brandAdapter;

    @Test
    @DisplayName("Given a brand, the brand should be saved correctly")
    void When_BrandIsCorrect_Expect_BrandSavedSuccessfully() {

        Brand brand = new Brand(1L, "Electronics", "Electronic items");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronic items");

        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandEntityMapper.toModel(brandEntity)).thenReturn(brand);

        Brand result = brandAdapter.addBrand(brand);

        assertEquals(result, brand, "Brand was not saved correctly");

    }

}