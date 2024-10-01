package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.util.CustomPage;

import java.util.List;

public interface IArticleServicePort {

    Article addArticle(Article article, List<String> categories, String brand);
    CustomPage getArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String brandName, String categoryName);

    void updateStock(Long articleId, Integer amount);
}
