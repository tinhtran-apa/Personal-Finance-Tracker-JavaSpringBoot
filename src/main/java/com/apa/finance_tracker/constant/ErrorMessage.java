package com.apa.finance_tracker.constant;

public class ErrorMessage {
    private ErrorMessage() {
    }

    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static final String TRANSACTION_ALREADY_EXISTS = "Transaction already exists";
    public static final String TRANSACTION_TYPE_MISMATCH = "Transaction type must match category type";

    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
}
