package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.adapters.driving.http.dto.response.ArticleResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IArticleRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestControllerAdapter {

    private final IArticleServicePort articleServicePort;
    private final IArticleRequestMapper articleRequestMapper;
    private final IArticleResponseMapper articleResponseMapper;

    @Operation(summary = "Add a new article")
    @ApiResponse(responseCode = "201", description = "Article created successfully")
    @ApiResponse(responseCode = "400", description = "Wrong article information")
    @PostMapping("/")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody AddArticleRequest addArticleRequest) {
        Article article = articleRequestMapper.addArticleRequestToArticle(addArticleRequest);
        Article savedArticle = articleServicePort.addArticle(article, addArticleRequest.getCategories());
        ArticleResponse response = articleResponseMapper.articleToArticleResponse(savedArticle);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
