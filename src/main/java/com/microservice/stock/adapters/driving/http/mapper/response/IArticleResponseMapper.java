package com.microservice.stock.adapters.driving.http.mapper.response;

import com.microservice.stock.adapters.driving.http.dto.response.ArticleResponse;
import com.microservice.stock.adapters.driving.http.dto.response.CategoryResponse;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface IArticleResponseMapper {

    ArticleResponse articleToArticleResponse(Article article);
    List<ArticleResponse> articleListToArticleResponseList(List<Article> articles);


}
