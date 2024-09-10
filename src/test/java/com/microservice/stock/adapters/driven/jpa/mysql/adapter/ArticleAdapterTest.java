package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.microservice.stock.domain.model.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

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

        Article article = new Article(1L, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("1000.00"));
        ArticleEntity articleEntity = new ArticleEntity(1L, "Laptop", "Description", 10L, new BigDecimal("1000.00"), new ArrayList<>());

        when(articleEntityMapper.articleToArticleEntity(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(articleEntity);
        when(articleEntityMapper.articleEntityToArticle(articleEntity)).thenReturn(article);

        Article result = articleAdapter.addArticle(article);

        assertEquals(article, result, "Article was not saved correctly");
        verify(articleRepository).save(articleEntity);
    }
}
