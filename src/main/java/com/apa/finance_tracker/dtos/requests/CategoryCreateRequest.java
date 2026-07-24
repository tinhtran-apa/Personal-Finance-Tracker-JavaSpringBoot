package com.apa.finance_tracker.dtos.requests;

import com.apa.finance_tracker.constants.ValidationMessage;
import com.apa.finance_tracker.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = ValidationMessage.CATEGORY_NAME_REQUIRED)
    @Size(min = 2, max = 100, message = ValidationMessage.CATEGORY_NAME_SIZE)
    private String name;

    @NotNull(message = ValidationMessage.CATEGORY_TYPE_REQUIRED)
    private CategoryType type;

    @NotNull(message = ValidationMessage.CATEGORY_ICON_REQUIRED)
    private String icon;
}
