package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.adapters.driving.http.dto.response.ArticleResponse;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IArticleRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleRestControllerAdapter.class)
class ArticleRestControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IArticleServicePort articleServicePort;

    @MockBean
    private IArticleRequestMapper articleRequestMapper;

    @MockBean
    private IArticleResponseMapper articleResponseMapper;


    @Test
    @DisplayName("Given an article, should return created status and the created article")
    void When_ArticleIsCorrect_Expect_ReturnCreatedStatus() throws Exception {

        Brand brand = new Brand(1L, "brand", "description");
        Category category = new Category(1L, "category", "description");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "category");
        Article article = new Article(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        ArticleResponse articleResponse = new ArticleResponse(1L, "name", "description", 10L, new BigDecimal("1000.00"), brand, List.of(categoryResponse));

        when(articleRequestMapper.addArticleRequestToArticle(any(AddArticleRequest.class))).thenReturn(article);
        when(articleServicePort.addArticle(any(Article.class), anyList(), anyString())).thenReturn(article);
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"name\",\"description\":\"description\",\"quantity\":10,\"price\":1000.00,\"brand\":\"brand\", \"categories\":[\"category\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.brand.name").value("brand"))
                .andExpect(jsonPath("$.categories[0].name").value("category"));
    }

    @Test
    @DisplayName("Given request parameters, should return paginated list of articles")
    void whenGetArticles_thenReturnCustomPageOfArticleResponse() throws Exception {

        Brand brand = new Brand(1L, "BrandName", "Brand Description");
        Category category = new Category(1L, "CategoryName", "Category Description");
        Article article = new Article(1L, "ArticleName", "Article Description", 10L, new BigDecimal("1000.00"), brand, List.of(category));
        ArticleResponse articleResponse = new ArticleResponse(1L, "ArticleName", "Article Description", 10L, new BigDecimal("1000.00"), brand, List.of(new CategoryResponse(1L, "CategoryName")));

        CustomPage<Article> articlesCustomPage = new CustomPage<>(List.of(article), 0, 10, 1L, 1);

        when(articleServicePort.getArticles(0, 10, "name", "asc", null, null)).thenReturn(articlesCustomPage);
        when(articleResponseMapper.articleListToArticleResponseList(articlesCustomPage.getContent())).thenReturn(List.of(articleResponse));

        mockMvc.perform(get("/article/")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("ArticleName"))
                .andExpect(jsonPath("$.content[0].description").value("Article Description"))
                .andExpect(jsonPath("$.content[0].quantity").value(10))
                .andExpect(jsonPath("$.content[0].price").value(1000.00))
                .andExpect(jsonPath("$.content[0].brand.name").value("BrandName"))
                .andExpect(jsonPath("$.content[0].categories[0].name").value("CategoryName"))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(articleServicePort).getArticles(0, 10, "name", "asc", null, null);
        verify(articleResponseMapper).articleListToArticleResponseList(articlesCustomPage.getContent());
    }
}


