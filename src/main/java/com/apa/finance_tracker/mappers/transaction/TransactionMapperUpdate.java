package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.requests.TransactionUpdateRequest;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;

public class TransactionMapperUpdate {
    public Transaction toEntityUpdate(TransactionUpdateRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        Category category = new Category();
        category.setId(request.getCategoryId());
        transaction.setCategory(category);
        return transaction;
    }

    public void updateEntity(Transaction target, Transaction source) {
        target.setAmount(source.getAmount());
        target.setType(source.getType());
        target.setDescription(source.getDescription());
        target.setTransactionDate(source.getTransactionDate());
    }
}
