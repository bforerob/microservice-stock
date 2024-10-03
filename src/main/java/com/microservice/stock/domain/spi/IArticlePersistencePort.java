package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.CustomPage;

public interface IArticlePersistencePort {

    Article addArticle(Article article);
    CustomPage<Article> getArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String brandName, String categoryName);

    void updateStock(Long articleId, Integer amount);
}
