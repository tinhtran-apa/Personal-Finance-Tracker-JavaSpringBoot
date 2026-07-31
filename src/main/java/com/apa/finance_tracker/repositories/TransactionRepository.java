package com.apa.finance_tracker.repositories;

import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import com.apa.finance_tracker.projection.CategorySummaryProjection;
import com.apa.finance_tracker.projection.DashboardSummaryProjection;
import com.apa.finance_tracker.projection.TransactionSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    boolean existsByCategoryId(Long categoryId);
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type = :type")
    BigDecimal getTotalAmountByType(@Param("type") TransactionType type);
    @Query("""
    SELECT
        c.id AS categoryId,
        c.name AS categoryName,
        COALESCE(SUM(t.amount), 0) AS total,
        COUNT(t.id) AS transactionCount
    FROM Transaction t
    JOIN t.category c
    WHERE t.user.id = :userId
      AND (:type IS NULL OR t.type = :type)
    GROUP BY c.id, c.name
    ORDER BY c.name
""")
    List<CategorySummaryProjection> getSummaryByCategory(
            Long userId,
            TransactionType type
    );

    @Query("""
    SELECT
        EXTRACT(MONTH FROM t.transactionDate) AS month,
        t.type AS type,
        SUM(t.amount) AS total
    FROM Transaction t
    WHERE EXTRACT(YEAR FROM t.transactionDate) = :year
      AND t.user.id = :userId
    GROUP BY EXTRACT(MONTH FROM t.transactionDate), t.type
    ORDER BY EXTRACT(MONTH FROM t.transactionDate), t.type
""")
    List<TransactionSummaryProjection> getSummaryByType(
            Long userId,
            Integer year
    );

    @Query("""
    SELECT
        COUNT(DISTINCT t.category.id) AS categoryCount,
        COUNT(t.id) AS transactionCount
    FROM Transaction t
    WHERE t.user.id = :userId
      AND EXTRACT(YEAR FROM t.transactionDate) = :year
    """)
    DashboardSummaryProjection getDashboardSummary(
            Long userId,
            Integer year
    );
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
}
