package com.apa.finance_tracker.service;
import com.apa.finance_tracker.dto.request.CategoryCreateRequest;
import com.apa.finance_tracker.dto.request.CategoryUpdateRequest;
import com.apa.finance_tracker.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest categoryCreateRequest);
    CategoryResponse getCategoryById(Long categoryId);
    CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest categoryUpdateRequest);
    String deleteCategory(Long categoryId);
    List<CategoryResponse> getAllCategory();
}
