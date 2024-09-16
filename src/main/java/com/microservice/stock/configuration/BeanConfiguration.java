package com.microservice.stock.configuration;

import com.microservice.stock.adapters.driven.jpa.mysql.adapter.ArticleAdapter;
import com.microservice.stock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.microservice.stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.api.IArticleServicePort;
import com.microservice.stock.domain.api.IBrandServicePort;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.api.usecase.ArticleUseCase;
import com.microservice.stock.domain.api.usecase.BrandUseCase;
import com.microservice.stock.domain.api.usecase.CategoryUseCase;
import com.microservice.stock.domain.spi.IArticlePersistencePort;
import com.microservice.stock.domain.spi.IBrandPersistencePort;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;


    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IArticlePersistencePort articlePersistencePort() {
        return new ArticleAdapter(articleRepository, articleEntityMapper);
    }

    @Bean
    public IArticleServicePort articleServicePort() {
        return new ArticleUseCase(articlePersistencePort(), categoryPersistencePort(), brandPersistencePort());
    }

}
