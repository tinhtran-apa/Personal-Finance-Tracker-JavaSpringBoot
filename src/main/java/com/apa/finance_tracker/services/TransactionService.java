package com.apa.finance_tracker.services;

import com.apa.finance_tracker.dto.responses.TransactionSummaryResponse;
import com.apa.finance_tracker.entitys.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TransactionService {
    Page<Transaction> getAllTransaction(Pageable pageable);

    Transaction getTransactionById(Long transactionId);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Long transactionId, Transaction transaction);

    TransactionSummaryResponse getSummary();

    void deleteTransaction(Long transactionId);

}
