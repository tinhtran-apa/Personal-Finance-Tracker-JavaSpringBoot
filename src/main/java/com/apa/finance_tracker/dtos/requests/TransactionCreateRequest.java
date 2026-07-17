package com.apa.finance_tracker.dtos.requests;

import com.apa.finance_tracker.constants.ValidationMessage;
import com.apa.finance_tracker.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionCreateRequest {
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

    public TransactionCreateRequest() {
    }

    public TransactionCreateRequest(BigDecimal amount, String description,TransactionType type, LocalDate transactionDate, Long category) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.transactionDate = transactionDate;
        this.categoryId = category;
    }
}
