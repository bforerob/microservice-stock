package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleAdapterTest {

    @Mock
    private IArticleRepository articleRepository;
    @Mock
    private IArticleEntityMapper articleEntityMapper;
    @InjectMocks
    private ArticleAdapter articleAdapter;

    @Test
    @DisplayName("Given an article, the article should be saved correctly")
    void when_ArticleIsCorrect_expect_ArticleSavedSuccessfully() {

        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        BrandEntity brandEntity = new BrandEntity(1L, "brand", "description");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "category", "description");
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        ArticleEntity articleEntity = new ArticleEntity(1L, "name", "Description", 10L, new BigDecimal("1000.00"), brandEntity, List.of(categoryEntity));

        when(articleEntityMapper.articleToArticleEntity(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(articleEntity);

        Article result = articleAdapter.addArticle(article);

        assertEquals(article, result, "Article was not saved correctly");
        verify(articleRepository).save(articleEntity);
    }

    @Test
    @DisplayName("Given articles in the repository, when calling getArticles, should return paginated list of articles")
    void When_ValidRequest_thenReturnCustomPageOfArticles() {

        // Mock del PageRequest y Page<ArticleEntity>
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        ArticleEntity articleEntity = new ArticleEntity(1L, "ArticleName", "Article Description", 10L, new BigDecimal("1000.00"), new BrandEntity(1L, "BrandName", "Brand Description"), List.of(new CategoryEntity(1L, "CategoryName", "Category Description")));
        Page<ArticleEntity> articlesPage = new PageImpl<>(List.of(articleEntity), pageRequest, 1);

        // Mock del repository
        when(articleRepository.findByBrandNameAndCategoryName(null, null, pageRequest)).thenReturn(articlesPage);

        // Mock del mapper
        Article article = new Article(1L, "ArticleName", "Article Description", 10L, new BigDecimal("1000.00"), new Brand(1L, "BrandName", "Brand Description"), List.of(new Category(1L, "CategoryName", "Category Description")));
        when(articleEntityMapper.articleEntityPageToModelList(articlesPage)).thenReturn(List.of(article));

        CustomPage<Article> result = articleAdapter.getArticles(0, 10, "name", "asc", null, null);

        assertEquals(1, result.getContent().size());
        assertEquals(article.getName(), result.getContent().get(0).getName());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(articleRepository).findByBrandNameAndCategoryName(null, null, pageRequest);
        verify(articleEntityMapper).articleEntityPageToModelList(articlesPage);
    }
}
