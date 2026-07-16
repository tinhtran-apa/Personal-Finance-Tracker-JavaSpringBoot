package com.apa.finance_tracker.services;
import com.apa.finance_tracker.entitys.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategory();
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(Long categoryId);
}
