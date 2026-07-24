package com.apa.finance_tracker.projection;

import java.math.BigDecimal;

public interface CategorySummaryProjection {

    Long getCategoryId();

    String getCategoryName();

    BigDecimal getTotal();
}
