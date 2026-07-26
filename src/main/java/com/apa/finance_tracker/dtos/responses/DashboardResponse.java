package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.projection.TransactionSummaryProjection;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal balance;

    private Long categoryCount;

    private Long transactionCount;

    private List<TransactionSummaryProjection> monthlySummary;

}