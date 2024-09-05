package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.InvalidPageNumberException;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.InvalidPageSizeException;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.model.CustomPage;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public CustomPage<Category> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        if (pageNumber<0) {
            throw new InvalidPageNumberException(pageNumber.toString());
        }

        if (pageSize<=0) {
            throw new InvalidPageSizeException(pageSize.toString());
        }


        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortBy);
        Page<CategoryEntity> page = categoryRepository.findAll(pageRequest);

        if (pageNumber >= page.getTotalPages()) {
            throw new InvalidPageNumberException(pageNumber.toString());
        }

        List<Category> categories= categoryEntityMapper.toModelList(page);
        return new CustomPage<>(categories, pageNumber, pageSize, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

}
