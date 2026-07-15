package com.apa.finance_tracker.service.impl;

import com.apa.finance_tracker.dto.request.CategoryCreateRequest;
import com.apa.finance_tracker.dto.request.CategoryUpdateRequest;
import com.apa.finance_tracker.dto.response.CategoryResponse;
import com.apa.finance_tracker.entity.Category;
import com.apa.finance_tracker.mapper.CategoryMapper;
import com.apa.finance_tracker.repository.CategoryRepository;
import com.apa.finance_tracker.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if(categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category existed.");
        }
        Category category = categoryMapper.toEntityCreate(request);
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category category : categories) {
            responses.add(categoryMapper.toResponse(category));
        }
        return responses;
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryMapper.toResponse(getCategory(categoryId));
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = getCategory(categoryId);
        Category categoryUpdate = categoryMapper.toEntityUpdate(category, request);
        categoryRepository.save(categoryUpdate);
        return categoryMapper.toResponse(categoryUpdate);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        getCategory(categoryId);
        categoryRepository.deleteById(categoryId);
        return "Delete successful";
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
