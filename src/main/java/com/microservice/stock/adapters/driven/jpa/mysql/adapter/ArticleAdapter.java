package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.spi.IArticlePersistencePort;

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
        return articleEntityMapper.articleEntityToArticle(savedEntity);
    }
}
