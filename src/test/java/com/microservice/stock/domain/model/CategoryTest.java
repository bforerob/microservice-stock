package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("given a category with a null name, addCategory should throw an exception")
    void When_CategoryNameIsNull_Expect_NullFieldException() {

        assertThrows(NullFieldException.class, () -> new Category(1L, null, "Electronic items"),
                "Category constructor did not throw the expected NullFieldException when name is null");

    }

    @Test
    @DisplayName("given a category with a null description, should throw an exception")
    void When_CategoryDescriptionIsNull_Expect_NullFieldException() {

        assertThrows(NullFieldException.class, () -> new Category(1L, "Electronics", null),
                "Category constructor did not throw the expected NullFieldException when description is null");

    }
}