package com.microservice.stock.adapters.driven.jpa.mysql.mapper;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.domain.model.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {

    Article articleEntityToArticle(ArticleEntity articleEntity);
    ArticleEntity articleToArticleEntity(Article article);


}
