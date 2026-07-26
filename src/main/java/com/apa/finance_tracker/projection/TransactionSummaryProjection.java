package com.apa.finance_tracker.projection;

import com.apa.finance_tracker.enums.TransactionType;

import java.math.BigDecimal;

public interface TransactionSummaryProjection {
    Integer getMonth();
    TransactionType getType();
    BigDecimal getTotal();
}
