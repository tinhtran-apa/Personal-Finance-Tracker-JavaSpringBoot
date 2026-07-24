package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate transactionDate;
    private Long categoryId;
    private String categoryName;
    private String categoryIcon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
