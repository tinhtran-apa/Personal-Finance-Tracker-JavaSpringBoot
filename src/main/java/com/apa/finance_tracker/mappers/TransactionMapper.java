package com.apa.finance_tracker.mappers;

import com.apa.finance_tracker.dto.requests.TransactionCreateRequest;
import com.apa.finance_tracker.dto.requests.TransactionUpdateRequest;
import com.apa.finance_tracker.dto.responses.PageResponse;
import com.apa.finance_tracker.dto.responses.TransactionResponse;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import org.springframework.data.domain.Page;
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
        Category category = new Category();
        category.setId(request.getCategoryId());
        transaction.setCategory(category);
        return transaction;
    }

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

    public PageResponse<TransactionResponse> toResponsePage(Page<Transaction> page) {

        PageResponse<TransactionResponse> response = new PageResponse<>();

        response.setItems(toResponseList(page.getContent()));
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());

        return response;
    }
}
