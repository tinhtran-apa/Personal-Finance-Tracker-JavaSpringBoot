package com.apa.finance_tracker.dtos.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryResponse {

    private Long categoryId;

    private String categoryName;

    private BigDecimal total;

}
