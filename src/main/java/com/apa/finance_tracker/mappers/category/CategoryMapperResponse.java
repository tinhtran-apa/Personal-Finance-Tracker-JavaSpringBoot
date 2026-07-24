package com.apa.finance_tracker.mappers.category;

import com.apa.finance_tracker.dtos.responses.CategoryResponse;
import com.apa.finance_tracker.entitys.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapperResponse {
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .icon(category.getIcon())
                .createdAt(category.getCreatedAt())
                .build();
    }


    public List<CategoryResponse> toResponseList(List<Category> category) {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category categories : category) {
            responses.add(toResponse(categories));
        }
        return responses;
    }
}
