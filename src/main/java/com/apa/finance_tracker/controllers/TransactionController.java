package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dto.requests.TransactionCreateRequest;
import com.apa.finance_tracker.dto.requests.TransactionUpdateRequest;
import com.apa.finance_tracker.dto.responses.ApiResponse;
import com.apa.finance_tracker.dto.responses.PageResponse;
import com.apa.finance_tracker.dto.responses.TransactionResponse;
import com.apa.finance_tracker.dto.responses.TransactionSummaryResponse;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.mappers.TransactionMapper;
import com.apa.finance_tracker.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionCreateRequest request) {
        Transaction transaction = transactionMapper.toEntityCreate(request);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        TransactionResponse response = transactionMapper.toResponse(savedTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.TRANSACTION_CREATED, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TransactionResponse>>> getAllCategory(Pageable pageable) {
        Page<Transaction> transaction = transactionService.getAllTransaction(pageable);
        PageResponse<TransactionResponse> response = transactionMapper.toResponsePage(transaction);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_LIST_RETRIEVED, response));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getCategoryById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        TransactionResponse response = transactionMapper.toResponse(transaction);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_RETRIEVED, response));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateCategory(@Valid @PathVariable Long transactionId, @RequestBody TransactionUpdateRequest request) {
        Transaction transaction = transactionMapper.toEntityUpdate(request);
        Transaction savedTransaction = transactionService.updateTransaction(transactionId, transaction);
        TransactionResponse response = transactionMapper.toResponse(savedTransaction);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_UPDATED, response));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long transactionId){
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_DELETED));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<TransactionSummaryResponse>> getSummary() {
        return  ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_SUMMARY, transactionService.getSummary()));
    }
}
