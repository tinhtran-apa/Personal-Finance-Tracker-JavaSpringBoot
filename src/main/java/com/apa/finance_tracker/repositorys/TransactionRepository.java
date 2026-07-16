package com.apa.finance_tracker.repositorys;

import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByCategoryId(Long categoryId);
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type = :type")
    BigDecimal getTotalAmountByType(@Param("type") TransactionType type);
}
