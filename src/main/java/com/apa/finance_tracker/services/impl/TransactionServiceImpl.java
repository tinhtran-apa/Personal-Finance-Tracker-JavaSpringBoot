package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.helpers.SecurityHelper;
import com.apa.finance_tracker.mappers.transaction.TransactionMapperUpdate;
import com.apa.finance_tracker.projection.CategorySummaryProjection;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.CategoryService;
import com.apa.finance_tracker.services.TransactionService;
import com.apa.finance_tracker.specifications.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final SecurityHelper securityHelper;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Category category = categoryService.getCategoryById(transaction.getCategory().getId());
        if(!transaction.getType().name().equals(category.getType().name())) {
            throw new BusinessException(ErrorMessage.TRANSACTION_TYPE_MISMATCH);
        }
        transaction.setCategory(category);
        transaction.setUser(securityHelper.getCurrentUser());
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return getTransaction(transactionId, securityHelper.getCurrentUser().getId());
    }

    @Override
    public Page<Transaction> getAllTransaction(TransactionType type, Long categoryId, LocalDate from, LocalDate to,String keyword, String searchBy,Pageable pageable) {
        Long userId = securityHelper.getCurrentUser().getId();
        Specification<Transaction> specification = TransactionSpecification.filter(userId, type, categoryId, from, to, keyword, searchBy);
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
        Transaction transaction = getTransaction(transactionId, securityHelper.getCurrentUser().getId());
        transactionRepository.delete(transaction);
    }

    @Override
    public List<CategorySummaryProjection> getSummaryByCategory(
            TransactionType type
    ) {
        return transactionRepository.getSummaryByCategory(
                securityHelper.getCurrentUser().getId(),
                type
        );
    }

    private Transaction getTransaction(Long transactionId, Long userId) {
        return transactionRepository.findByIdAndUserId(transactionId, userId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.TRANSACTION_NOT_FOUND));
    }
}
