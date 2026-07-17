package com.apa.finance_tracker.dtos.requests;

import com.apa.finance_tracker.constants.ValidationMessage;
import com.apa.finance_tracker.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateRequest {

    @NotBlank(message = ValidationMessage.CATEGORY_NAME_REQUIRED)
    @Size(min = 2, max = 100, message = ValidationMessage.CATEGORY_NAME_SIZE)
    private String name;

    @NotNull(message = ValidationMessage.CATEGORY_TYPE_REQUIRED)
    private CategoryType type;

    public CategoryCreateRequest() {
    }

    public CategoryCreateRequest(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }
}
