package com.apa.finance_tracker.repositories;

import com.apa.finance_tracker.entitys.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
