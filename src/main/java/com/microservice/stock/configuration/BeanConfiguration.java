package com.microservice.stock.configuration;

import com.microservice.stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.api.usecase.CategoryUseCase;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}
