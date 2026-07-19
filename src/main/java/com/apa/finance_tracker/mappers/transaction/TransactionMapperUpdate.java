package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.requests.TransactionUpdateRequest;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;

public class TransactionMapperUpdate {
    public Transaction toEntityUpdate(TransactionUpdateRequest request) {
        return Transaction.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .category(Category.builder().id(request.getCategoryId()).build())
                .build();
    }

    public void updateEntity(Transaction target, Transaction source) {
        target.setAmount(source.getAmount());
        target.setType(source.getType());
        target.setDescription(source.getDescription());
        target.setTransactionDate(source.getTransactionDate());
    }
}
