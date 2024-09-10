package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Article;

public interface IArticlePersistencePort {

    Article addArticle(Article article);

}
