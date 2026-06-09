package com.firstclub.repository;

import com.firstclub.entity.Subscription;
import com.firstclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUser(User user);

    Optional<Subscription> findByUserIdAndStatus(
            Long userId,
            String status
    );
}