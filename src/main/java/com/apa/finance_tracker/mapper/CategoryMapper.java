package com.apa.finance_tracker.mapper;

import com.apa.finance_tracker.dto.request.CategoryCreateRequest;
import com.apa.finance_tracker.dto.request.CategoryUpdateRequest;
import com.apa.finance_tracker.dto.response.CategoryResponse;
import com.apa.finance_tracker.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntityCreate(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }

    public Category toEntityUpdate(Category category, CategoryUpdateRequest request) {
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setType(category.getType());
        response.setCreatedAt(category.getCreatedAt());
        return response;
    }


}
