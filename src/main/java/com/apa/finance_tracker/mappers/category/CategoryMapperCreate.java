package com.apa.finance_tracker.mappers.category;

import com.apa.finance_tracker.dtos.requests.CategoryCreateRequest;
import com.apa.finance_tracker.entitys.Category;

public class CategoryMapperCreate {
    public Category toEntityCreate(CategoryCreateRequest request) {
        return Category.builder()
                .name(request.getName())
                .type(request.getType())
                .icon(request.getIcon())
                .build();
    }
}
