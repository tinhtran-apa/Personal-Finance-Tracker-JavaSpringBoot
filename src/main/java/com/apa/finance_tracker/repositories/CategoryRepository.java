package com.apa.finance_tracker.repositories;

import com.apa.finance_tracker.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndUserId(String name, Long userId);
    boolean existsByNameAndUserIdAndIdNot(
            String name,
            Long userId,
            Long id
    );
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    List<Category> findAllByUserId(Long userId);
}
