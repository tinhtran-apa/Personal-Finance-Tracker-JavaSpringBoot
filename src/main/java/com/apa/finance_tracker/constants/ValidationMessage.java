package com.apa.finance_tracker.constants;

public class ValidationMessage {
    public static final String CATEGORY_NAME_REQUIRED = "Category name is required";
    public static final String CATEGORY_NAME_SIZE = "Category name must be between 2 and 100 characters";
    public static final String CATEGORY_TYPE_REQUIRED = "Category type is required";

    public static final String TRANSACTION_AMOUNT_REQUIRED = "Transaction amount is required";
    public static final String TRANSACTION_AMOUNT_POSITIVE = "Transaction amount must be greater than 0";
    public static final String TRANSACTION_TYPE_REQUIRED = "Transaction type is required";
    public static final String TRANSACTION_DESCRIPTION_SIZE =  "Transaction description must not exceed 255 characters";
    public static final String TRANSACTION_DATE_REQUIRED = "Transaction date is required";
    public static final String CATEGORY_REQUIRED = "Category is required";

    public static final String INVALID_ENUM_VALUE = "Invalid enum value";
}
