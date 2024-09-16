package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.exception.*;
import com.microservice.stock.domain.model.Article;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.CustomPage;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.util.Validator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.microservice.stock.domain.util.DomainConstants.*;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;



    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Article addArticle(Article article, List<String> categoryNames, String brand) {

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
            throw new NotFoundException(Field.CATEGORIES+": "+missingCategoryNames);
        }

        Optional<Brand> optionalBrand = brandPersistencePort.findByName(brand);
        if (optionalBrand.isEmpty()){
            throw new NotFoundException((Field.BRAND+": "+brand));
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
        article.setBrand(optionalBrand.get());
        return articlePersistencePort.addArticle(article);
    }

    @Override
    public CustomPage<Article> getArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection, String brandName, String categoryName) {

        Validator.validateCustomPage(pageNumber, pageSize, sortBy, GET_ARTICLES_SORT_PARAMETER, sortDirection);

        return articlePersistencePort.getArticles(pageNumber, pageSize, sortBy, sortDirection, brandName, categoryName);
    }
}
