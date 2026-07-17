package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.requests.TransactionCreateRequest;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;

public class TransactionMapperCreate {
    public Transaction toEntityCreate(TransactionCreateRequest request) {
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
}
