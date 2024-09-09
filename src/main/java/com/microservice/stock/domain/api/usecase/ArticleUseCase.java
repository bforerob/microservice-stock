package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exception.*;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.microservice.stock.domain.util.DomainConstants.MAX_ARTICLE_CATEGORIES_NUMBER;
import static com.microservice.stock.domain.util.DomainConstants.MIN_ARTICLE_CATEGORIES_NUMBER;
import static java.util.stream.Collectors.toList;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;



    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Article addArticle(Article article, List<String> categoryNames) {

        if(categoryNames.size() < MIN_ARTICLE_CATEGORIES_NUMBER
                || categoryNames.size() > MAX_ARTICLE_CATEGORIES_NUMBER) {
            throw new ArticleCategoriesNumberException();
        }
        if (categoryNames.size() != new HashSet<>(categoryNames).size()) {
            throw new DuplicatedArticleCategoriesException();
        }
        List<Category> categories = categoryPersistencePort.findCategoriesByNames(categoryNames);
        if (categories.size() != categoryNames.size()) {

            List<String> foundCategoryNames = categories.stream()
                    .map(Category::getName)
                    .toList();
            List<String> missingCategoryNames = categoryNames.stream()
                    .filter(name -> !foundCategoryNames.contains(name))
                    .toList();
            throw new CategoryNotFoundException((missingCategoryNames).toString());
        }
        if (article.getName().trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        if(article.getQuantity()<0){
            throw new NegativeFieldException(DomainConstants.Field.QUANTITY.toString());
        }
        if(article.getPrice().compareTo(BigDecimal.ZERO)<0){
            throw new NegativeFieldException(DomainConstants.Field.PRICE.toString());
        }


        article.setCategories(categories);
        return articlePersistencePort.addArticle(article);
    }
}
