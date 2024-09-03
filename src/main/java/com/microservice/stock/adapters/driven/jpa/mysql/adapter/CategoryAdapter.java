package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Category addCategory(Category category) {

        CategoryEntity savedEntity = categoryRepository.save(categoryEntityMapper.toEntity(category));

        return categoryEntityMapper.toModel(savedEntity);
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ArrayList<>();
        }
        return categoryEntityMapper.toModelList(categories);
    }

    @Override
    public Boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

}
