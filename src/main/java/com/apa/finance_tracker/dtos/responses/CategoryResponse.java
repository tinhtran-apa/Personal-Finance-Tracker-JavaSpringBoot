package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryType type;
    private LocalDateTime createdAt;

    public CategoryResponse() {}

    public CategoryResponse(Long id, String name, CategoryType type, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }
}
