package com.microservice.stock.domain.api.usecase;

import com.microservice.stock.domain.exception.*;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.model.CustomPage;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;
import com.microservice.stock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    @Test
    @DisplayName("given a category the category should be added correctly")
    void When_CategoryInformationIsCorrect_Expect_CategoryShouldBeAddedSuccessfully() {
        Category category = new Category(1L, "Electronics", "Electronic items");

        when(categoryPersistencePort.addCategory(category)).thenReturn(category);

        Category result = categoryUseCase.addCategory(category);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category, result);
    }

    @Test
    @DisplayName("given a category with an empty name, addCategory should throw an exception")
    void Expect_EmptyFieldException_When_CategoryNameIsEmpty() {
        Category category = new Category(1L, "", "Electronic items");

        assertThrows(EmptyFieldException.class, () -> categoryUseCase.addCategory(category)
                , "addCategory did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a category with a name that exceeds the character limit, addCategory should throw an exception")
    void Expect_LengthFieldException_When_CategoryNameExceedsCharacterLimit() {
        String longName = "A".repeat(DomainConstants.MAX_CHARACTERS_CATEGORY_NAME + 1);
        Category category = new Category(1L, longName, "Electronic items");

        assertThrows(LengthFieldException.class, () -> categoryUseCase.addCategory(category)
                , "addCategory did not throw the length field exception");

    }

    @Test
    @DisplayName("given a category with a name that already exists, addCategory should throw a exception")
    void Expect_CategoryAlreadyExistsException_When_CategoryNameAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Electronic items");
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> categoryUseCase.addCategory(category)
                , "addCategory did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a category with an empty description, addCategory should throw an exception")
    void Expect_EmptyFieldException_When_CategoryDescriptionIsEmpty() {
        Category category = new Category(1L, "Electronics", "");

        assertThrows(EmptyFieldException.class, () -> categoryUseCase.addCategory(category)
                , "addCategory did not throw the already exists exception");

    }

    @Test
    @DisplayName("given a category with a description that exceeds the character limit, addCategory should throw an exception")
    void Expect_LengthFieldException_When_CategoryDescriptionExceedsCharacterLimit() {
        String longDescription = "A".repeat(DomainConstants.MAX_CHARACTERS_CATEGORY_DESCRIPTION + 1);
        Category category = new Category(1L, "Electronics", longDescription);

        assertThrows(LengthFieldException.class, () -> categoryUseCase.addCategory(category)
                , "addCategory did not throw the length field exception");

    }

    @Test
    @DisplayName("Should return paginated categories list when all inputs are valid")
    void When_getAllCategoriesValidInput_Expect_ReturnsPaginatedCategories() {

        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "ASC";

        List<Category> categories = List.of(new Category(1L, "Electronics", "Electronic items"));
        CustomPage<Category> expectedPage = new CustomPage<>(categories, pageNumber, pageSize, 1L, 1);

        when(categoryPersistencePort.getAllCategories(pageNumber, pageSize, sortBy, sortDirection))
                .thenReturn(expectedPage);

        CustomPage<Category> result = categoryUseCase.getAllCategories(pageNumber, pageSize, sortBy, sortDirection);

        assertEquals(expectedPage, result, "getAllCategories did not return expected page categories");
        verify(categoryPersistencePort, times(1)).getAllCategories(pageNumber, pageSize, sortBy, sortDirection);
    }

    @Test
    @DisplayName("Should throw InvalidSortDirectionException when sortDirection is invalid")
    void _Expect_InvalidSortDirectionException_SortDirectionIsInvalid() {

        Integer pageNumber = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String invalidSortDirection = "invalid";

        InvalidSortDirectionException exception = assertThrows(InvalidSortDirectionException.class, () ->
                categoryUseCase.getAllCategories(pageNumber, pageSize, sortBy, invalidSortDirection)
        );

        assertEquals(invalidSortDirection, exception.getMessage(), "getAllCategories did not throw the expected  invalid sort direction exception");
        verify(categoryPersistencePort, never()).getAllCategories(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw InvalidSortParameterException when sort parameter  is invalid")
    void Expect_ThrowsInvalidSortParameterException_When_SortParameterIsInvalid() {

        Integer pageNumber = 0;
        Integer pageSize = 10;
        String invalidSortBy = "invalid";
        String sortDirection = "ASC";

        InvalidSortParameterException exception = assertThrows(InvalidSortParameterException.class, () ->
                categoryUseCase.getAllCategories(pageNumber, pageSize, invalidSortBy, sortDirection)
        );

        assertEquals(invalidSortBy, exception.getMessage(), "getAllCategories did not throw the expected invalid sort parameter exception");
        verify(categoryPersistencePort, never()).getAllCategories(anyInt(), anyInt(), anyString(), anyString());
    }


}