package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    @DisplayName("given a brand with a null name, addBrand should throw an exception")
    void When_BrandNameIsNull_Expect_NullFieldException() {

        assertThrows(NullFieldException.class, () -> new Brand(1L, null, "Electronic items"),
                "Brand constructor did not throw the expected NullFieldException when name is null");

    }

    @Test
    @DisplayName("given a brand with a null description, should throw an exception")
    void When_BrandDescriptionIsNull_Expect_NullFieldException() {

        assertThrows(NullFieldException.class, () -> new Brand(1L, "Electronics", null),
                "Brand constructor did not throw the expected NullFieldException when description is null");

    }


}