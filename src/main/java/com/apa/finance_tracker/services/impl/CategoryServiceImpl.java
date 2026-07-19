package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.mappers.category.CategoryMapperUpdate;
import com.apa.finance_tracker.repositories.CategoryRepository;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Category createCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())){
            throw new DuplicateResourceException(ErrorMessage.CATEGORY_ALREADY_EXISTS);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return getCategory(categoryId);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category existCategory = getCategory(categoryId);
        if (existCategory.getType() != category.getType()
                && transactionRepository.existsByCategoryId(categoryId)) {

            throw new BusinessException(
                    ErrorMessage.CATEGORY_HAS_TRANSACTIONS
            );
        }
        if (categoryRepository.existsByNameAndIdNot(category.getName(), categoryId)) {
            throw new DuplicateResourceException(ErrorMessage.CATEGORY_ALREADY_EXISTS);
        }
        new CategoryMapperUpdate().updateEntity(existCategory, category);
        return categoryRepository.save(existCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        getCategory(categoryId);
        if(transactionRepository.existsByCategoryId(categoryId)) {
            throw new BusinessException(ErrorMessage.CATEGORY_HAS_TRANSACTIONS);
        }
        categoryRepository.deleteById(categoryId);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }
}
