package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Article;

import java.util.List;

public interface IArticleServicePort {

    Article addArticle(Article article, List<String> categories);

}
