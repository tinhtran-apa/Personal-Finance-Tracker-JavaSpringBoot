package com.apa.finance_tracker.mapper;

import com.apa.finance_tracker.dto.request.TransactionCreateRequest;
import com.apa.finance_tracker.dto.request.TransactionUpdateRequest;
import com.apa.finance_tracker.dto.response.TransactionResponse;
import com.apa.finance_tracker.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMapper {
    public Transaction toEntityCreate(TransactionCreateRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        return transaction;
    }

    public Transaction toEntityUpdate(TransactionUpdateRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());
        return transaction;
    }

    public void updateEntity(Transaction target, Transaction source) {
        target.setAmount(source.getAmount());
        target.setType(source.getType());
        target.setDescription(source.getDescription());
        target.setTransactionDate(source.getTransactionDate());
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setDescription(transaction.getDescription());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setCategoryId(transaction.getCategory().getId());
        response.setCategoryName(transaction.getCategory().getName());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUpdatedAt(transaction.getUpdatedAt());
        return response;
    }

    public List<TransactionResponse> toResponseList(List<Transaction> transaction) {
        List<TransactionResponse> responses = new ArrayList<>();
        for(Transaction trans : transaction) {
            responses.add(toResponse(trans));
        }
        return responses;
    }
}
