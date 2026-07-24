package com.apa.finance_tracker.repositories;

import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.projection.CategorySummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    boolean existsByCategoryId(Long categoryId);
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type = :type")
    BigDecimal getTotalAmountByType(@Param("type") TransactionType type);
    @Query("""
    SELECT
        c.id AS categoryId,
        c.name AS categoryName,
        COALESCE(SUM(t.amount), 0) AS total
    FROM Transaction t
    JOIN t.category c
    WHERE (:type IS NULL OR t.type = :type)
    GROUP BY c.id, c.name
    ORDER BY c.name
""")
    List<CategorySummaryProjection> getSummaryByCategory(TransactionType type);
}
