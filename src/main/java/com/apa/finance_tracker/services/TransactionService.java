package com.apa.finance_tracker.services;

import com.apa.finance_tracker.dtos.responses.TransactionSummaryResponse;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface TransactionService {
    Page<Transaction> getAllTransaction(TransactionType type, Long categoryId, LocalDate from, LocalDate to, Pageable pageable);

    Transaction getTransactionById(Long transactionId);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Long transactionId, Transaction transaction);

    TransactionSummaryResponse getSummary();

    void deleteTransaction(Long transactionId);

}
