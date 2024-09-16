package com.microservice.stock.adapters.driven.jpa.mysql.repository;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @Query("SELECT a FROM ArticleEntity a WHERE " +
            "(:brandName IS NULL OR a.brand.name = :brandName) AND " +
            "(:categoryName IS NULL OR :categoryName IN (SELECT c.name FROM a.categories c))")
    Page<ArticleEntity> findByBrandNameAndCategoryName(@Param("brandName") String brandName,
                                                       @Param("categoryName") String categoryName,
                                                       Pageable pageable);

}
