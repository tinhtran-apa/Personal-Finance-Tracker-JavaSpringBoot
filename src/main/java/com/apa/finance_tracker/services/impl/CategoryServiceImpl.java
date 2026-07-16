package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.mappers.CategoryMapper;
import com.apa.finance_tracker.repositorys.CategoryRepository;
import com.apa.finance_tracker.repositorys.TransactionRepository;
import com.apa.finance_tracker.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final TransactionRepository transactionRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.transactionRepository = transactionRepository;
    }

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
        categoryMapper.updateEntity(existCategory, category);
        return categoryRepository.save(existCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        getCategory(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
    }
}
