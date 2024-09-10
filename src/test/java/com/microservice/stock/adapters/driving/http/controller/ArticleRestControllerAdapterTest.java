package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.adapters.driving.http.dto.response.ArticleResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IArticleRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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

        Article article = new Article(1L, new ArrayList<>(), "Laptop", "Description", 10L, new BigDecimal("1000.00"));
        ArticleResponse articleResponse = new ArticleResponse(1L,  new ArrayList<>(),"Laptop", "Description", 10L, new BigDecimal("1000.00"));

        when(articleRequestMapper.addArticleRequestToArticle(any(AddArticleRequest.class))).thenReturn(article);
        when(articleServicePort.addArticle(any(Article.class), anyList())).thenReturn(article);
        when(articleResponseMapper.articleToArticleResponse(any(Article.class))).thenReturn(articleResponse);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Laptop\",\"description\":\"Description\",\"quantity\":10,\"price\":1000.00,\"categories\":[\"Electronics\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.quantity").value(10L))
                .andExpect(jsonPath("$.price").value(1000.00));

        verify(articleServicePort).addArticle(any(Article.class), anyList());
        verify(articleResponseMapper).articleToArticleResponse(any(Article.class));
    }
}
