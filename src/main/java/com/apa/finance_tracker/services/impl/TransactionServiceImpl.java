package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.dtos.responses.TransactionSummaryResponse;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.mappers.transaction.TransactionMapperUpdate;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.CategoryService;
import com.apa.finance_tracker.services.TransactionService;
import com.apa.finance_tracker.specifications.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Category category = categoryService.getCategoryById(transaction.getCategory().getId());
        if(!transaction.getType().name().equals(category.getType().name())) {
            throw new BusinessException(ErrorMessage.TRANSACTION_TYPE_MISMATCH);
        }
        transaction.setCategory(category);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return getTransaction(transactionId);
    }

    @Override
    public Page<Transaction> getAllTransaction(TransactionType type, Long categoryId, LocalDate from, LocalDate to,Pageable pageable) {
        Specification<Transaction> specification = TransactionSpecification.filter(type, categoryId, from, to);
        return transactionRepository.findAll(specification, pageable);
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Transaction transaction) {
        Transaction existTransaction = getTransactionById(transactionId);

        Category category = categoryService.getCategoryById(transaction.getCategory().getId());

        if(!transaction.getType().name().equals(category.getType().name())) {
            throw new BusinessException(ErrorMessage.TRANSACTION_TYPE_MISMATCH);
        }

        new TransactionMapperUpdate().updateEntity(existTransaction, transaction);
        existTransaction.setCategory(category);
        return transactionRepository.save(existTransaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        getTransaction(transactionId);
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public TransactionSummaryResponse getSummary() {
        BigDecimal income = transactionRepository.getTotalAmountByType(TransactionType.INCOME);
        BigDecimal expense = transactionRepository.getTotalAmountByType(TransactionType.EXPENSE);
        TransactionSummaryResponse response = new TransactionSummaryResponse();
        response.setTotalIncome(income);
        response.setTotalExpense(expense);
        response.setBalance(income.subtract(expense));
        return response;
    }

    private Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND));
    }
}
