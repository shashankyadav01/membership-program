package com.firstclub.repository;

import com.firstclub.entity.TierHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierHistoryRepository
        extends JpaRepository<TierHistory, Long> {
}