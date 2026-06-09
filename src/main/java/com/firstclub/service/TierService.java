package com.firstclub.service;

import com.firstclub.entity.MembershipTier;
import com.firstclub.entity.User;
import com.firstclub.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TierService {

    private final MembershipTierRepository tierRepository;

    // Existing APIs used by MembershipController

    public List<MembershipTier> getAllTiers() {
        return tierRepository.findAll();
    }

    public MembershipTier createTier(MembershipTier tier) {
        return tierRepository.save(tier);
    }

    // Auto tier evaluation logic

    public MembershipTier evaluateTier(User user) {

        if (
                user.getTotalOrders() >= 50
                        || user.getMonthlyOrderValue() >= 10000
                        || "VIP".equalsIgnoreCase(user.getCohort())
        ) {

            return tierRepository.findById(3L)
                    .orElseThrow(() ->
                            new RuntimeException("PLATINUM tier not found"));
        }

        if (
                user.getTotalOrders() >= 20
                        || user.getMonthlyOrderValue() >= 5000
                        || "PREMIUM".equalsIgnoreCase(user.getCohort())
        ) {

            return tierRepository.findById(2L)
                    .orElseThrow(() ->
                            new RuntimeException("GOLD tier not found"));
        }

        return tierRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("SILVER tier not found"));
    }
}