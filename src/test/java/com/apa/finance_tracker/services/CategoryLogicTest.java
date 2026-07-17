package com.apa.finance_tracker.services;

import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.enums.CategoryType;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.repositories.CategoryRepository;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
class CategoryLogicTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void givenValidCategory_whenCreateCategory_thenReturnCreatedCategory() {
        Category category = new Category();
        category.setName("Food");
        category.setType(CategoryType.EXPENSE);
        given(categoryRepository.existsByName("Food")).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.createCategory(category);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Food");
        assertThat(result.getType()).isEqualTo(CategoryType.EXPENSE);
        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    void givenDuplicateCategoryName_whenCreateCategory_thenThrowDuplicateResourceException() {
        Category category = new Category();
        category.setName("Food");
        given(categoryRepository.existsByName("Food")).willReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(category)).isInstanceOf(DuplicateResourceException.class);
        then(categoryRepository).should(never()).save(any());
    }

    @Test
    void givenExistingCategoryId_whenGetCategory_thenReturnCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Food");
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Food");
    }

    @Test
    void givenInvalidCategoryId_whenGetCategory_thenThrowResourceNotFoundException() {
        given(categoryRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.getCategoryById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void givenValidCategory_whenUpdateCategory_thenReturnUpdatedCategory() {
        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setName("Food");
        oldCategory.setType(CategoryType.EXPENSE);
        Category newCategory = new Category();
        newCategory.setName("Meal");
        newCategory.setType(CategoryType.EXPENSE);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(oldCategory));
        given(categoryRepository.existsByNameAndIdNot("Meal", 1L)).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.updateCategory(1L, newCategory);

        assertThat(result.getName().equals("Meal"));
        then(categoryRepository).should().save(oldCategory);
    }

    @Test
    void givenDuplicateCategoryName_whenUpdateCategory_thenThrowDuplicateResourceException() {
        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setName("Food");
        Category newCategory = new Category();
        newCategory.setName("Meal");
        given(categoryRepository.findById(1L)).willReturn(Optional.of(oldCategory));
        given(categoryRepository.existsByNameAndIdNot("Meal", 1L)).willReturn(true);

        assertThatThrownBy(() -> categoryService.updateCategory(1L, newCategory)).isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    void givenExistingCategoryId_whenDeleteCategory_thenDeleteSuccessfully() {
        Category category = new Category();
        category.setId(1L);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(transactionRepository.existsByCategoryId(1L)).willReturn(false);

        categoryService.deleteCategory(1L);

        then(categoryRepository).should().deleteById(1L);
    }

    @Test
    void givenCategoryUsedByTransaction_whenDeleteCategory_thenThrowBusinessException() {
        Category category = new Category();
        category.setId(1L);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(transactionRepository.existsByCategoryId(1L)).willReturn(true);

        assertThatThrownBy(() -> categoryService.deleteCategory(1L)).isInstanceOf(BusinessException.class);
    }
}