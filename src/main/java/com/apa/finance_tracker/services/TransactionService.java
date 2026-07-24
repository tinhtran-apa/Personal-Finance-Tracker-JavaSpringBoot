package com.apa.finance_tracker.services;

import com.apa.finance_tracker.dtos.responses.CategorySummaryResponse;
import com.apa.finance_tracker.dtos.responses.TransactionSummaryResponse;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.projection.CategorySummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface TransactionService {
    Page<Transaction> getAllTransaction(TransactionType type, Long categoryId, LocalDate from, LocalDate to, String keyword, String searchBy,Pageable pageable);

    Transaction getTransactionById(Long transactionId);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Long transactionId, Transaction transaction);

    TransactionSummaryResponse getSummary();

    void deleteTransaction(Long transactionId);

    List<CategorySummaryProjection> getSummaryByCategory(
            TransactionType type
    );
}
