package com.apa.finance_tracker.services;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.CategoryType;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.repositories.CategoryRepository;
import com.apa.finance_tracker.repositories.TransactionRepository;
import com.apa.finance_tracker.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class TransactionLogicTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    private Category createExpenseCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Food");
        category.setType(CategoryType.EXPENSE);
        return category;
    }

    private Category createIncomeCategory() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Salary");
        category.setType(CategoryType.INCOME);
        return category;
    }

    private Transaction createExpenseTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setType(TransactionType.EXPENSE);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setCategory(createExpenseCategory());
        return transaction;
    }

    @Test
    void givenValidTransaction_whenCreateTransaction_thenReturnCreatedTransaction() {
        Category category = createExpenseCategory();
        Transaction transaction = createExpenseTransaction();
        given(categoryService.getCategoryById(1L)).willReturn(category);
        given(transactionRepository.save(any(Transaction.class))).willAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.createTransaction(transaction);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(100));
        then(transactionRepository).should().save(any(Transaction.class));
    }

    @Test
    void givenInvalidCategoryId_whenCreateTransaction_thenThrowResourceNotFoundException() {
        Transaction transaction = createExpenseTransaction();
        given(categoryService.getCategoryById(1L)).willThrow(new ResourceNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));

        assertThatThrownBy(() -> transactionService.createTransaction(transaction)).isInstanceOf(ResourceNotFoundException.class);
        then(transactionRepository).should(never()).save(any());
    }

    @Test
    void givenCategoryTypeMismatch_whenCreateTransaction_thenThrowBusinessException() {
        Category category = createIncomeCategory();
        Transaction transaction = createExpenseTransaction();

        given(categoryService.getCategoryById(1L)).willReturn(category);

        assertThatThrownBy(() -> transactionService.createTransaction(transaction)).isInstanceOf(BusinessException.class);
        then(transactionRepository).should(never()).save(any());
    }

    @Test
    void givenValidTransaction_whenUpdateTransaction_thenReturnUpdatedTransaction() {
        Transaction oldTransaction = createExpenseTransaction();
        Transaction updateTransaction = createExpenseTransaction();
        updateTransaction.setAmount(BigDecimal.valueOf(300));
        given(transactionRepository.findById(1L)).willReturn(Optional.of(oldTransaction));
        given(categoryService.getCategoryById(1L)).willReturn(createExpenseCategory());
        given(transactionRepository.save(any(Transaction.class))).willAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.updateTransaction(1L, updateTransaction);

        assertThat(result.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(300));
    }

    @Test
    void givenExistingTransactionId_whenDeleteTransaction_thenDeleteSuccessfully() {
        Transaction transaction = createExpenseTransaction();
        given(transactionRepository.findById(1L)).willReturn(Optional.of(transaction));

        transactionService.deleteTransaction(1L);
        then(transactionRepository).should().deleteById(1L);
    }
}