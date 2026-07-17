package com.apa.finance_tracker.mappers.category;

import com.apa.finance_tracker.dtos.requests.CategoryCreateRequest;
import com.apa.finance_tracker.entitys.Category;

public class CategoryMapperCreate {
    public Category toEntityCreate(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        return category;
    }
}
