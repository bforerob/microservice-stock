package com.microservice.stock.adapters.driving.http.controller;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.adapters.driving.http.dto.request.IncrementStockRequest;
import com.microservice.stock.adapters.driving.http.dto.response.ArticleResponse;
import com.microservice.stock.adapters.driving.http.mapper.request.IArticleRequestMapper;
import com.microservice.stock.adapters.driving.http.mapper.response.IArticleResponseMapper;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.CustomPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        Article savedArticle = articleServicePort.addArticle(articleRequestMapper.addArticleRequestToArticle(addArticleRequest), addArticleRequest.getCategories(), addArticleRequest.getBrand());
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseMapper.articleToArticleResponse(savedArticle));


    }
    @GetMapping("/")
    public ResponseEntity<CustomPage<ArticleResponse>> getArticles(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String categoryName){

        CustomPage<Article> articlesCustomPage = articleServicePort.getArticles(pageNumber, pageSize, sortBy, sortDirection, brandName, categoryName);
        List<ArticleResponse> articleResponse = articleResponseMapper.articleListToArticleResponseList(articlesCustomPage.getContent());

        return ResponseEntity.ok(new CustomPage<>(articleResponse, articlesCustomPage.getPageNumber(),
                articlesCustomPage.getPageSize(), articlesCustomPage.getTotalElements(), articlesCustomPage.getTotalPages()));

    }

    @PostMapping("/increment")
        public ResponseEntity<String> incrementStock(@RequestBody IncrementStockRequest incrementStockRequest) {
        articleServicePort.updateStock(incrementStockRequest.getArticleId(), incrementStockRequest.getQuantity());
        return ResponseEntity.ok("Stock updated successfully");
    }

}
