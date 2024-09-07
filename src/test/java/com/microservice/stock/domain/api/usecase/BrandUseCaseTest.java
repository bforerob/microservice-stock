package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.exception.AlreadyExistsException;
import com.microservice.stock.domain.exception.EmptyFieldException;
import com.microservice.stock.domain.exception.LengthFieldException;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {


    @Mock
    private IBrandPersistencePort brandPersistencePort;
    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    @DisplayName("given a brand the brand should be added correctly")
    void When_BrandInformationIsCorrect_Expect_BrandShouldBeAddedSuccessfully() {
        Brand brand = new Brand(1L, "Electronics", "Electronic brand");

        when(brandPersistencePort.addBrand(brand)).thenReturn(brand);

        Brand result = brandUseCase.addBrand(brand);

        assertNotNull(result);
        assertEquals(brand.getId(), result.getId());
        assertEquals(brand, result);
    }

    @Test
    @DisplayName("given a brand with an empty name, addBrand should throw an exception")
    void Expect_EmptyFieldException_When_BrandNameIsEmpty() {
        Brand brand = new Brand(1L, "", "Electronic brand");

        assertThrows(EmptyFieldException.class, () -> brandUseCase.addBrand(brand)
                , "addBrand did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a brand with a name that exceeds the character limit, addBrand should throw an exception")
    void Expect_LengthFieldException_When_BrandNameExceedsCharacterLimit() {
        String longName = "A".repeat(DomainConstants.MAX_CHARACTERS_BRAND_NAME + 1);
        Brand brand = new Brand(1L, longName, "Electronic brand");

        assertThrows(LengthFieldException.class, () -> brandUseCase.addBrand(brand)
                , "addBrand did not throw the length field exception");

    }

    @Test
    @DisplayName("given a brand with a name that already exists, addBrand should throw a exception")
    void Expect_BrandAlreadyExistsException_When_BrandNameAlreadyExists() {
        Brand brand = new Brand(1L, "Electronics", "Electronic brand");
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> brandUseCase.addBrand(brand)
                , "addBrand did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a brand with an empty description, addBrand should throw an exception")
    void Expect_EmptyFieldException_When_BrandDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Electronics", "");

        assertThrows(EmptyFieldException.class, () -> brandUseCase.addBrand(brand)
                , "addBrand did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a brand with a description that exceeds the character limit, addBrand should throw an exception")
    void Expect_LengthFieldException_When_BrandDescriptionExceedsCharacterLimit() {
        String longDescription = "A".repeat(DomainConstants.MAX_CHARACTERS_BRAND_DESCRIPTION + 1);
        Brand brand = new Brand(1L, "Electronics", longDescription);

        assertThrows(LengthFieldException.class, () -> brandUseCase.addBrand(brand)
                , "addBrand did not throw the length field exception");

    }

}