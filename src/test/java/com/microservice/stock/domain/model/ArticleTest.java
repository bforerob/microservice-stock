package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    @DisplayName("given an article with a null name, should throw NullFieldException")
    void when_ArticleNameIsNull_expect_NullFieldException() {
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, null, "description", 10L, new BigDecimal("1000.00"), brand, List.of(category)),
                "Article constructor did not throw the expected NullFieldException when name is null"
        );
    }

    @Test
    @DisplayName("given an article with a null description, should throw NullFieldException")
    void when_ArticleDescriptionIsNull_expect_NullFieldException() {
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, "name", null, 10L, new BigDecimal("1000.00"), brand, List.of(category)),
                "Article constructor did not throw the expected NullFieldException when description is null"
        );
    }

    @Test
    @DisplayName("given an article with a null quantity, should throw NullFieldException")
    void when_ArticleQuantityIsNull_expect_NullFieldException() {
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, "name", "description", null, new BigDecimal("1000.00"), brand, List.of(category)),
                "Article constructor did not throw the expected NullFieldException when quantity is null"
        );
    }

    @Test
    @DisplayName("given an article with a null price, should throw NullFieldException")
    void when_ArticlePriceIsNull_expect_NullFieldException() {
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, "name", "description", 10L, null, brand, List.of(category)),
                "Article constructor did not throw the expected NullFieldException when price is null"
        );
    }

    @Test
    @DisplayName("given an article with valid fields, should create the article successfully")
    void when_ArticleFieldsAreValid_expect_ArticleCreatedSuccessfully() {
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        Article article = new  Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));

        assertNotNull(article);
        assertEquals("name", article.getName());
        assertEquals("description", article.getDescription());
        assertEquals(10L, article.getQuantity());
        assertEquals(new BigDecimal("1000.00"), article.getPrice());
    }
}
