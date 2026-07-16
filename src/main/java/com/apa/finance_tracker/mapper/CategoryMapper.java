package com.apa.finance_tracker.mapper;

import com.apa.finance_tracker.dto.request.CategoryCreateRequest;
import com.apa.finance_tracker.dto.request.CategoryUpdateRequest;
import com.apa.finance_tracker.dto.response.CategoryResponse;
import com.apa.finance_tracker.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public Category toEntityCreate(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }

    public Category toEntityUpdate(CategoryUpdateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }


    public void updateEntity(Category target, Category source) {
        target.setName(source.getName());
        target.setType(source.getType());
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setType(category.getType());
        response.setCreatedAt(category.getCreatedAt());
        return response;
    }


    public List<CategoryResponse> toResponseList(List<Category> category) {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category categories : category) {
            responses.add(toResponse(categories));
        }
        return responses;
    }
}
