package com.microservice.stock.adapters.driven.jpa.mysql.repository;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
}
