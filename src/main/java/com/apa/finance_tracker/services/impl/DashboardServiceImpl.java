package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.dtos.responses.DashboardResponse;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.helpers.SecurityHelper;
import com.apa.finance_tracker.projection.DashboardSummaryProjection;
import com.apa.finance_tracker.projection.TransactionSummaryProjection;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.DashboardService;
import com.apa.finance_tracker.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final TransactionRepository transactionRepository;
    private final SecurityHelper securityHelper;

    @Override
    public DashboardResponse getDashboard(Integer year) {
        User currentUser = securityHelper.getCurrentUser();
        List<TransactionSummaryProjection> monthlySummary =
                transactionRepository.getSummaryByType(currentUser.getId(), year);

        DashboardSummaryProjection dashboardSummary =
                transactionRepository.getDashboardSummary(currentUser.getId(), year);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (TransactionSummaryProjection item : monthlySummary) {

            if (item.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(item.getTotal());
            } else {
                totalExpense = totalExpense.add(item.getTotal());
            }

        }

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(totalIncome.subtract(totalExpense))
                .categoryCount(dashboardSummary.getCategoryCount())
                .transactionCount(dashboardSummary.getTransactionCount())
                .monthlySummary(monthlySummary)
                .build();
    }
}
