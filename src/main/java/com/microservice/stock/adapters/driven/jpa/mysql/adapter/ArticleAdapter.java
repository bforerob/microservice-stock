package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.util.CustomPage;
import jakarta.transaction.Transactional;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


public class ArticleAdapter implements IArticlePersistencePort {

    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    public ArticleAdapter(IArticleRepository articleRepository, IArticleEntityMapper articleEntityMapper) {
        this.articleRepository = articleRepository;
        this.articleEntityMapper = articleEntityMapper;
    }


    @Override
    public Article addArticle(Article article) {

        ArticleEntity articleEntity = articleEntityMapper.articleToArticleEntity(article);
        ArticleEntity savedEntity = articleRepository.save(articleEntity);
        article.setId(savedEntity.getId());
        return article;
    }

    @Override
    public CustomPage<Article> getArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String brandName, String categoryName) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortBy);
        Page<ArticleEntity> articlesPage = articleRepository.findByBrandNameAndCategoryName(brandName, categoryName, pageRequest);

        List<Article> articles = articleEntityMapper.articleEntityPageToModelList(articlesPage);
        return new CustomPage<>(articles, pageNumber, pageSize, articlesPage.getTotalElements(), articlesPage.getTotalPages());
    }

    @Transactional
    @Override
    public void updateStock(Long articleId, Integer amount) {
        articleRepository.incrementQuantity(articleId, amount);
    }
}
