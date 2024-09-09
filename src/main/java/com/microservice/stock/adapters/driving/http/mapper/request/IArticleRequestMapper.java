package com.microservice.stock.adapters.driving.http.mapper.request;

import com.microservice.stock.adapters.driving.http.dto.request.AddArticleRequest;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IArticleRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Article addArticleRequestToArticle(AddArticleRequest addArticleRequest);




}
