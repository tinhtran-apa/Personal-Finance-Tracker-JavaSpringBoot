package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.CategoryType;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryType type;
    private LocalDateTime createdAt;
}
