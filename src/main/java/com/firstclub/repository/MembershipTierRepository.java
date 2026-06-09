package com.firstclub.repository;

import com.firstclub.entity.MembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipTierRepository
        extends JpaRepository<MembershipTier, Long> {

    Optional<MembershipTier> findByTierName(String tierName);

}