package com.apa.finance_tracker.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionSummaryResponse {

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal balance;

    public TransactionSummaryResponse() {
    }

    public TransactionSummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }
}
