package com.microservice.stock.adapters.driven.jpa.mysql.adapter;

import com.microservice.stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageNumberException;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageSizeException;
import com.microservice.stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.microservice.stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.CustomPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    void When_CategoryIsCorrect_Expect_CategorySavedSuccessfully() {

        Category category = new Category(1L, "Electronics", "Electronic items");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic items");

        when(categoryEntityMapper.categoryToCategoryEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryEntityMapper.categoryEntityToCategory(categoryEntity)).thenReturn(category);

        Category result = categoryAdapter.addCategory(category);

        assertEquals(result, category, "Category was not saved correctly");

    }



    @Test
    @DisplayName("Given a valid request, should return a custom page")
    void When_ValidRequest_Expect_ReturnCustomPage() {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "ASC";

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortBy);
        Page<CategoryEntity> mockPage = new PageImpl<>(List.of(new CategoryEntity()));
        List<Category> categories = List.of(new Category(1L, "name", "description"));


        when(categoryRepository.findAll(pageRequest)).thenReturn(mockPage);
        when(categoryEntityMapper.categoryEntityPageToModelList(mockPage)).thenReturn(categories);

        CustomPage<Category> result = categoryAdapter.getAllCategories(pageNumber, pageSize, sortBy, sortDirection);


        assertEquals(pageNumber, result.getPageNumber(), "Page number is wrong");
        assertEquals(pageSize, result.getPageSize(), "Page size is wrong");
        assertEquals(categories, result.getContent(), "Content is not the expected");
        assertEquals(mockPage.getTotalElements(), result.getTotalElements(), "Total elements number is not the expected");
        assertEquals(mockPage.getTotalPages(), result.getTotalPages(), "Total page number is not the expected");

        verify(categoryRepository).findAll(pageRequest);
        verify(categoryEntityMapper).categoryEntityPageToModelList(mockPage);
    }

    @Test
    @DisplayName("Given a negative page number, should Throw NegativePageNumberException")

    void Expect_ThrowNegativePageNumberException_When_NegativePageNumber() {

        Integer pageNumber = -1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "ASC";

        assertThrows(NegativePageNumberException.class,
                () -> categoryAdapter.getAllCategories(pageNumber, pageSize, sortBy, sortDirection), "did not throw the NegativePageNumberException");
    }

    @Test
    @DisplayName("Given a negative page size, should Throw NegativePageSizeException")
    void Expect_ThrowNegativePageNumberException_When_NegativePageSize() {

        Integer pageNumber = 1;
        Integer pageSize = 0;
        String sortBy = "name";
        String sortDirection = "ASC";

        assertThrows(NegativePageSizeException.class,
                () -> categoryAdapter.getAllCategories(pageNumber, pageSize, sortBy, sortDirection), "did not throw the NegativePageSizeException");
    }

    @Test
    void When_CategoryNameAlreadyExist_Expect_True() {

        String name = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic gadgets");

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(categoryEntity));

        Boolean result = categoryAdapter.existsByName(name);

        assertTrue(result);
    }
}