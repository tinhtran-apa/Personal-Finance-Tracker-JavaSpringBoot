package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.requests.TransactionCreateRequest;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;

public class TransactionMapperCreate {
    public Transaction toEntityCreate(TransactionCreateRequest request) {
        return Transaction.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .category(Category.builder().id(request.getCategoryId()).build())
                .build();
    }
}
