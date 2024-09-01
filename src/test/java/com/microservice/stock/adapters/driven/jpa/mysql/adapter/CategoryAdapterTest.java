package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @Test
    @DisplayName("Given a category, the category should be saved correctly")
    void When_CategoryIsCorrect_Expect_CategoryShouldBeSavedSuccessfully() {

        Category category = new Category(1L, "Electronics", "Electronic items");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic items");

        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryEntityMapper.toModel(categoryEntity)).thenReturn(category);

        Category result = categoryAdapter.addCategory(category);

        assertEquals(result, category, "Category was not saved correctly");

    }

    @Test
    void testExistsByName() {

        String name = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic gadgets");

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(categoryEntity));

        Boolean result = categoryAdapter.existsByName(name);

        assertTrue(result);
    }
}