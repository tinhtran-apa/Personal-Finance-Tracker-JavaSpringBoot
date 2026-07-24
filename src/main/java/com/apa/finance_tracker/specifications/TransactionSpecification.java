package com.apa.finance_tracker.specifications;

import com.apa.finance_tracker.entitys.Category;
import com.apa.finance_tracker.entitys.Transaction;
import com.apa.finance_tracker.enums.TransactionType;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {
    public static Specification<Transaction> filter(
            TransactionType type,
            Long categoryId,
            LocalDate from,
            LocalDate to,
            String keyword,
            String searchBy
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (from != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), from));
            }

            if (to != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("transactionDate"), to));
            }

            if (keyword != null && !keyword.isBlank()) {

                if ("amount".equals(searchBy)) {

                    try {
                        predicates.add(
                                cb.equal(
                                        root.get("amount"),
                                        new BigDecimal(keyword)
                                )
                        );
                    } catch (NumberFormatException ignored) {
                    }

                } else {

                    Join<Transaction, Category> category =
                            root.join("category");

                    predicates.add(
                            cb.like(
                                    cb.lower(category.get("name")),
                                    "%" + keyword.toLowerCase() + "%"
                            )
                    );
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
