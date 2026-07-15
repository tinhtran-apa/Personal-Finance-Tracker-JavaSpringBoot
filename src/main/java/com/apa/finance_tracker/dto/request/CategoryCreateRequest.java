package com.apa.finance_tracker.dto.request;

import com.apa.finance_tracker.enums.CategoryType;

public class CategoryCreateRequest {
    private String name;
    private CategoryType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }
}
