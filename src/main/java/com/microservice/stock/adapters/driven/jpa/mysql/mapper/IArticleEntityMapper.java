package com.microservice.stock.adapters.driven.jpa.mysql.mapper;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.microservice.stock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticleEntityMapper {

    Article articleEntityToArticle(ArticleEntity articleEntity);
    @Mapping(target = "brand", source = "brand.id")
    ArticleEntity articleToArticleEntity(Article article);
    default BrandEntity mapBrandIdToBrandEntity(Long brandId) {
        if (brandId == null) {
            return null;
        }
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        return brandEntity;
    }
    List<Article> articleEntityPageToModelList(Page<ArticleEntity> articleEntityPage);

}
