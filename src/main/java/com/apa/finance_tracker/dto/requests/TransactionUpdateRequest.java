package com.apa.finance_tracker.dto.requests;

import com.apa.finance_tracker.constants.ValidationMessage;
import com.apa.finance_tracker.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
public class TransactionUpdateRequest {
    @NotNull(message = ValidationMessage.TRANSACTION_AMOUNT_REQUIRED)
    @Positive(message = ValidationMessage.TRANSACTION_AMOUNT_POSITIVE)
    private BigDecimal amount;

    @NotNull(message = ValidationMessage.TRANSACTION_TYPE_REQUIRED)
    private TransactionType type;

    @Size(max = 255, message = ValidationMessage.TRANSACTION_DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = ValidationMessage.TRANSACTION_DATE_REQUIRED)
    private LocalDate transactionDate;

    @NotNull(message = ValidationMessage.CATEGORY_REQUIRED)
    private Long categoryId;

    public TransactionUpdateRequest() {
    }

    public TransactionUpdateRequest(BigDecimal amount, String description,TransactionType type, LocalDate transactionDate, Long category) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.transactionDate = transactionDate;
        this.categoryId = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
