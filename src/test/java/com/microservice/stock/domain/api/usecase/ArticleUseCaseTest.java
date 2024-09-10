package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.exception.ArticleCategoriesNumberException;
import com.microservice.stock.domain.exception.CategoryNotFoundException;
import com.microservice.stock.domain.exception.DuplicatedArticleCategoriesException;
import com.microservice.stock.domain.exception.NegativeFieldException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @Test
    @DisplayName("Should add article successfully when all data is correct")
    void when_AllDataIsCorrect_expect_ArticleShouldBeAddedSuccessfully() {
        // Arrange
        Article article = new Article(null, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("1000.00"));
        List<String> categoryNames = List.of("Electronics");
        List<Category> categories = List.of(new Category(1L, "Electronics", "Electronic items"));

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);
        when(articlePersistencePort.addArticle(article)).thenReturn(article);

        // Act
        Article result = articleUseCase.addArticle(article, categoryNames);

        // Assert
        assertNotNull(result);
        assertEquals(article.getName(), result.getName());
        assertEquals(categories, result.getCategories());
    }

    @Test
    @DisplayName("Should throw exception when number of categories is less than minimum")
    void when_CategoriesLessThanMinimum_expect_ArticleCategoriesNumberException() {
        List<Category> categories = new ArrayList<>();
        Article article = new Article(1L, categories, "Article Name", "Description", 10L, new BigDecimal("100.00"));        List<String> categoryNames = List.of();

        assertThrows(ArticleCategoriesNumberException.class, () -> articleUseCase.addArticle(article, categoryNames));
    }

    @Test
    @DisplayName("Should throw exception when categories are duplicated")
    void when_CategoriesAreDuplicated_expect_DuplicatedArticleCategoriesException() {
        List<Category> categories = new ArrayList<>();
        Article article = new Article(1L, categories, "Article Name", "Description", 10L, new BigDecimal("100.00"));        List<String> categoryNames = List.of("Electronics", "Electronics");

        assertThrows(DuplicatedArticleCategoriesException.class, () -> articleUseCase.addArticle(article, categoryNames));
    }

    @Test
    @DisplayName("Should throw exception when a category name does not exist")
    void when_CategoryNameDoesNotExist_expect_CategoryNotFoundException() {
        Article article = new Article(null, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("1000.00"));
        List<String> categoryNames = List.of("NonExistingCategory");
        List<Category> categories = List.of(); // No matching categories found

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);

        assertThrows(CategoryNotFoundException.class, () -> articleUseCase.addArticle(article, categoryNames));
    }

    @Test
    @DisplayName("Should throw exception when price is negative")
    void when_PriceIsNegative_expect_NegativeFieldException() {
        Article article = new Article(null, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("-1000.00"));
        List<String> categoryNames = List.of("Electronics");
        List<Category> categories = List.of(new Category(1L, "Electronics", "Electronic items"));

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);


        assertThrows(NegativeFieldException.class, () -> articleUseCase.addArticle(article, categoryNames));
    }

    @Test
    @DisplayName("Should throw exception when quantity is negative")
    void when_QuantityIsNegative_expect_NegativeFieldException() {
        Article article = new Article(null, new ArrayList<>(), "Laptop", "Description", -10L, new BigDecimal("1000.00"));
        List<String> categoryNames = List.of("Electronics");
        List<Category> categories = List.of(new Category(1L, "Electronics", "Electronic items"));

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);

        assertThrows(NegativeFieldException.class, () -> articleUseCase.addArticle(article, categoryNames));
    }
}

