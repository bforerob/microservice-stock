package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.domain.exception.ArticleCategoriesNumberException;
import com.microservice.stock.domain.exception.NotFoundException;
import com.microservice.stock.domain.exception.DuplicatedArticleCategoriesException;
import com.microservice.stock.domain.exception.NegativeFieldException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;


    @InjectMocks
    private ArticleUseCase articleUseCase;


    @Test
    @DisplayName("Should add article successfully when all data is correct")
    void when_AllDataIsCorrect_expect_ArticleShouldBeAddedSuccessfully() {

        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        AddArticleRequest addArticleRequest = new AddArticleRequest("name", "description",10L, new BigDecimal("1000.00"), "brand", List.of("category"));
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        List<String> categoryNames = List.of("category");
        List<Category> categories = List.of(new Category(1L, "category", "description"));

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);
        when(articlePersistencePort.addArticle(article)).thenReturn(article);
        when(brandPersistencePort.findByName(addArticleRequest.getBrand())).thenReturn(Optional.of(brand));

        Article result = articleUseCase.addArticle(article, categoryNames, addArticleRequest.getBrand());

        assertNotNull(result);
        assertEquals(article.getName(), result.getName());
        assertEquals(categories, result.getCategories());
    }

    @Test
    @DisplayName("Should throw exception when number of categories is less than minimum")
    void when_CategoriesLessThanMinimum_expect_ArticleCategoriesNumberException() {

        Category category = new Category(1L, "category", "description");
        Brand brand = new Brand(1L, "brand", "description");
        String brandName = "brand";

        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        List<String> categoryNames = List.of();

        assertThrows(ArticleCategoriesNumberException.class, () -> articleUseCase.addArticle(article, categoryNames, brandName));
    }

    @Test
    @DisplayName("Should throw exception when categories are duplicated")
    void when_CategoriesAreDuplicated_expect_DuplicatedArticleCategoriesException() {

        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        List<String> categoryNames = List.of("category", "category");
        String brandName = "brand";
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));


        assertThrows(DuplicatedArticleCategoriesException.class, () -> articleUseCase.addArticle(article, categoryNames, brandName));
    }

    @Test
    @DisplayName("Should throw exception when a category name does not exist")
    void when_CategoryNameDoesNotExist_expect_CategoryNotFoundException() {

        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        String brandName = "brand";
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        List<String> categoryNames = List.of("category", "category2");
        List<Category> categories = List.of(new Category(1L, "category", "description"));


        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);

        assertThrows(NotFoundException.class, () -> articleUseCase.addArticle(article, categoryNames, brandName));
    }

    @Test
    @DisplayName("Should throw exception when price is negative")
    void when_PriceIsNegative_expect_NegativeFieldException() {

        Brand brand = new Brand(1L, "brand", "description");
        String brandName = "brand";
        Category category = new Category(1L, "category", "description");
        AddArticleRequest addArticleRequest = new AddArticleRequest("name", "description",10L, new BigDecimal("-1000.00"), brandName, List.of("category"));
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("-1000.00"), brand, List.of(category));
        List<String> categoryNames = List.of("category");
        List<Category> categories = List.of(new Category(1L, "category", "description"));


        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);
        when(brandPersistencePort.findByName(addArticleRequest.getBrand())).thenReturn(Optional.of(brand));


        assertThrows(NegativeFieldException.class, () -> articleUseCase.addArticle(article, categoryNames, brandName));
    }

    @Test
    @DisplayName("Should throw exception when quantity is negative")
    void when_QuantityIsNegative_expect_NegativeFieldException() {

        String brandName = "brand";
        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        AddArticleRequest addArticleRequest = new AddArticleRequest("name", "description",10L, new BigDecimal("1000.00"), brandName, List.of("category"));
        Article article = new Article(1L, "name", "description", -10L, new BigDecimal("1000.00"), brand, List.of(category));
        List<String> categoryNames = List.of("category");
        List<Category> categories = List.of(new Category(1L, "category", "description"));

        when(categoryPersistencePort.findCategoriesByNames(categoryNames)).thenReturn(categories);
        when(brandPersistencePort.findByName(addArticleRequest.getBrand())).thenReturn(Optional.of(brand));

        assertThrows(NegativeFieldException.class, () -> articleUseCase.addArticle(article, categoryNames, brandName));
    }

    @Test
    @DisplayName("Given valid parameters, should return paginated list of articles from the persistence port")
    void whenGetArticles_thenReturnCustomPageFromPersistencePort() {

        CustomPage<Article> articlesCustomPage = new CustomPage<>(List.of(new Article(1L, "ArticleName", "Description", 10L, new BigDecimal("1000.00"), new Brand(1L, "BrandName", "Description"), List.of(new Category(1L, "CategoryName", "Description")))), 0, 10, 1L, 1);

        when(articlePersistencePort.getArticles(0, 10, "name", "asc", null, null)).thenReturn(articlesCustomPage);

        CustomPage<Article> result = articleUseCase.getArticles(0, 10, "name", "asc", null, null);

        assertEquals(1, result.getContent().size());
        assertEquals("ArticleName", result.getContent().get(0).getName());

    }

}

