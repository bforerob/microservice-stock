package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exception.NullFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    @DisplayName("given an article with a null name, should throw NullFieldException")
    void when_ArticleNameIsNull_expect_NullFieldException() {
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, new ArrayList<>(), null, "Description", 10L, new BigDecimal("1000.00")),
                "Article constructor did not throw the expected NullFieldException when name is null"
        );
    }

    @Test
    @DisplayName("given an article with a null description, should throw NullFieldException")
    void when_ArticleDescriptionIsNull_expect_NullFieldException() {
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, new ArrayList<>(), "Laptop", null, 10L, new BigDecimal("1000.00")),
                "Article constructor did not throw the expected NullFieldException when description is null"
        );
    }

    @Test
    @DisplayName("given an article with a null quantity, should throw NullFieldException")
    void when_ArticleQuantityIsNull_expect_NullFieldException() {
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, new ArrayList<>(), "Laptop", "Description", null, new BigDecimal("1000.00")),
                "Article constructor did not throw the expected NullFieldException when quantity is null"
        );
    }

    @Test
    @DisplayName("given an article with a null price, should throw NullFieldException")
    void when_ArticlePriceIsNull_expect_NullFieldException() {
        assertThrows(NullFieldException.class, () ->
                        new Article(1L, new ArrayList<>(), "Laptop", "Description", 10L, null),
                "Article constructor did not throw the expected NullFieldException when price is null"
        );
    }

    @Test
    @DisplayName("given an article with valid fields, should create the article successfully")
    void when_ArticleFieldsAreValid_expect_ArticleCreatedSuccessfully() {
        Article article = new Article(1L, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("1000.00"));

        assertNotNull(article);
        assertEquals("Laptop", article.getName());
        assertEquals("Description", article.getDescription());
        assertEquals(10L, article.getQuantity());
        assertEquals(new BigDecimal("1000.00"), article.getPrice());
    }
}
