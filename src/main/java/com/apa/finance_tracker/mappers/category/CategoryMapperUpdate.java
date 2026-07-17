package com.apa.finance_tracker.mappers.category;

import com.apa.finance_tracker.dtos.requests.CategoryUpdateRequest;
import com.apa.finance_tracker.entitys.Category;

public class CategoryMapperUpdate {
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
}
