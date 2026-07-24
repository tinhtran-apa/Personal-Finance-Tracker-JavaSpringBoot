package com.apa.finance_tracker.mappers.transaction;

import com.apa.finance_tracker.dtos.responses.CategorySummaryResponse;
import com.apa.finance_tracker.dtos.responses.PageResponse;
import com.apa.finance_tracker.dtos.responses.TransactionResponse;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.projection.CategorySummaryProjection;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapperResponse {
    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .categoryId(transaction.getCategory().getId())
                .categoryName(transaction.getCategory().getName())
                .categoryIcon(transaction.getCategory().getIcon())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public List<TransactionResponse> toResponseList(List<Transaction> transaction) {
        List<TransactionResponse> responses = new ArrayList<>();
        for (Transaction trans : transaction) {
            responses.add(toResponse(trans));
        }
        return responses;
    }

    public PageResponse<TransactionResponse> toResponsePage(Page<Transaction> page) {
        return PageResponse.<TransactionResponse>builder()
                .items(toResponseList(page.getContent()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public CategorySummaryResponse toCategorySummaryResponse(
            CategorySummaryProjection projection
    ) {

        return CategorySummaryResponse.builder()
                .categoryId(projection.getCategoryId())
                .categoryName(projection.getCategoryName())
                .total(projection.getTotal())
                .build();
    }

    public List<CategorySummaryResponse> toCategorySummaryResponseList(
            List<CategorySummaryProjection> projections
    ) {

        List<CategorySummaryResponse> responses = new ArrayList<>();

        for (CategorySummaryProjection projection : projections) {
            responses.add(toCategorySummaryResponse(projection));
        }

        return responses;
    }
}
