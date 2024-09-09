package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exception.AlreadyExistsException;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;

import java.util.HashSet;
import java.util.List;

import static com.microservice.stock.domain.util.DomainConstants.MAX_ARTICLE_CATEGORIES_NUMBER;
import static com.microservice.stock.domain.util.DomainConstants.MIN_ARTICLE_CATEGORIES_NUMBER;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;



    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Article addArticle(Article article, List<String> categoryNames) {

        List<Category> categories = categoryPersistencePort.findCategoriesByNames(categoryNames);
        if(categoryNames.size() < MIN_ARTICLE_CATEGORIES_NUMBER
                || categoryNames.size() > MAX_ARTICLE_CATEGORIES_NUMBER) {
            throw new AlreadyExistsException(DomainConstants.Field.BRAND.toString());
        }
        if (categoryNames.size() != new HashSet<>(categoryNames).size()) {
            throw new AlreadyExistsException(DomainConstants.Field.BRAND.toString());
        }

        article.setCategories(categories);
        return articlePersistencePort.addArticle(article);
    }
}
