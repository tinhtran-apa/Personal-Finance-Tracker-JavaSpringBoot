package com.apa.finance_tracker.service;
import com.apa.finance_tracker.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategory();
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(Long categoryId);
}
