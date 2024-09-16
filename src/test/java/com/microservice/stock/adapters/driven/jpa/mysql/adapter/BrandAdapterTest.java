package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageNumberException;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageSizeException;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronic items", new ArrayList<>());

        when(brandEntityMapper.brandToBrandEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandEntityMapper.brandEntityToBrand(brandEntity)).thenReturn(brand);

        Brand result = brandAdapter.addBrand(brand);

        assertEquals(result, brand, "Brand was not saved correctly");

    }

    @Test
    @DisplayName("Given a valid request, should return a custom page with paginated brands")
    void When_ValidRequest_Expect_ReturnCustomPageOfBrand() {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "ASC";

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortBy);
        Page<BrandEntity> mockPage = new PageImpl<>(List.of(new BrandEntity()));
        List<Brand> brands = List.of(new Brand(1L, "name", "description"));

        when(brandRepository.findAll(pageRequest)).thenReturn(mockPage);
        when(brandEntityMapper.brandEntityPageToBrandList(mockPage)).thenReturn(brands);

        CustomPage<Brand> result = brandAdapter.getAllBrands(pageNumber, pageSize, sortBy, sortDirection);

        assertEquals(pageNumber, result.getPageNumber(), "Page number is wrong");
        assertEquals(pageSize, result.getPageSize(), "Page size is wrong");
        assertEquals(brands, result.getContent(), "Content is not the expected");
        assertEquals(mockPage.getTotalElements(), result.getTotalElements(), "Total elements number is not the expected");
        assertEquals(mockPage.getTotalPages(), result.getTotalPages(), "Total page number is not the expected");

        verify(brandRepository).findAll(pageRequest);
        verify(brandEntityMapper).brandEntityPageToBrandList(mockPage);
    }

    @Test
    @DisplayName("Given a negative page number, should throw NegativePageNumberException")
    void Expect_ThrowNegativePageNumberException_When_NegativePageNumber_Brand() {

        Integer pageNumber = -1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "ASC";

        assertThrows(NegativePageNumberException.class,
                () -> brandAdapter.getAllBrands(pageNumber, pageSize, sortBy, sortDirection),
                "did not throw the NegativePageNumberException");
    }

    @Test
    @DisplayName("Given a negative page size, should throw NegativePageSizeException")
    void Expect_ThrowNegativePageNumberException_When_NegativePageSize_Brand() {

        Integer pageNumber = 1;
        Integer pageSize = 0;
        String sortBy = "name";
        String sortDirection = "ASC";

        assertThrows(NegativePageSizeException.class,
                () -> brandAdapter.getAllBrands(pageNumber, pageSize, sortBy, sortDirection),
                "did not throw the NegativePageSizeException");
    }

    @Test
    void When_BrandNameAlreadyExist_Expect_True() {

        String name = "Nike";
        BrandEntity brandEntity = new BrandEntity(1L, "Nike", "Sports brand", new ArrayList<>());

        when(brandRepository.findByName(name)).thenReturn(Optional.of(brandEntity));

        Boolean result = brandAdapter.existsByName(name);

        assertTrue(result);
    }


}