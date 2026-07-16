package com.apa.finance_tracker.exceptions.handler;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.constants.ValidationMessage;
import com.apa.finance_tracker.dto.responses.ErrorResponse;
import com.apa.finance_tracker.constants.ErrorCode;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ErrorCode.RESOURCE_NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleResourceDuplicate(DuplicateResourceException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ErrorCode.DUPLICATE_RESOURCE,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorMessage.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.VALIDATION_ERROR,
                message
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.VALIDATION_ERROR,
                ValidationMessage.INVALID_ENUM_VALUE
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.BUSINESS_ERROR,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
