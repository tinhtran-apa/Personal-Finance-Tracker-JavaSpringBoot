package com.apa.finance_tracker.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryResponse {
    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal balance;

}
