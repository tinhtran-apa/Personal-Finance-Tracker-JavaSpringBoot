package com.apa.finance_tracker.dto.request;

import com.apa.finance_tracker.constant.ValidationMessage;
import com.apa.finance_tracker.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryUpdateRequest {

    @NotBlank(message = ValidationMessage.CATEGORY_NAME_REQUIRED)
    @Size(min = 2, max = 100, message = ValidationMessage.CATEGORY_NAME_SIZE)
    private String name;

    @NotNull(message = ValidationMessage.CATEGORY_TYPE_REQUIRED)
    private CategoryType type;
    public CategoryUpdateRequest() {
    }

    public CategoryUpdateRequest(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }

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
