package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.responses.PageResponse;
import com.apa.finance_tracker.dtos.responses.TransactionResponse;
import com.apa.finance_tracker.entitys.Transaction;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;

public class TransactionMapperResponse {
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
