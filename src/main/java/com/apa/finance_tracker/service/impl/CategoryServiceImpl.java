package com.apa.finance_tracker.service.impl;

import com.apa.finance_tracker.constant.ErrorMessage;
import com.apa.finance_tracker.entity.Category;
import com.apa.finance_tracker.exception.resource.DuplicateResourceException;
import com.apa.finance_tracker.exception.resource.ResourceNotFoundException;
import com.apa.finance_tracker.mapper.CategoryMapper;
import com.apa.finance_tracker.repository.CategoryRepository;
import com.apa.finance_tracker.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
