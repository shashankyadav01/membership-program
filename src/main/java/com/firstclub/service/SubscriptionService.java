package com.firstclub.service;

import com.firstclub.dto.SubscribeRequest;
import com.firstclub.entity.MembershipPlan;
import com.firstclub.entity.MembershipTier;
import com.firstclub.entity.Subscription;
import com.firstclub.entity.User;
import com.firstclub.repository.MembershipPlanRepository;
import com.firstclub.repository.MembershipTierRepository;
import com.firstclub.repository.SubscriptionRepository;
import com.firstclub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final MembershipPlanRepository membershipPlanRepository;
    private final MembershipTierRepository membershipTierRepository;
    private final TierService tierService;

    public Subscription subscribe(SubscribeRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        subscriptionRepository
                .findByUserIdAndStatus(user.getId(), "ACTIVE")
                .ifPresent(subscription -> {
                    throw new RuntimeException(
                            "User already has an active membership");
                });

        MembershipPlan plan =
                membershipPlanRepository.findById(request.getPlanId())
                        .orElseThrow(() ->
                                new RuntimeException("Plan not found"));

        MembershipTier tier =
                tierService.evaluateTier(user);

        LocalDate startDate = LocalDate.now();

        LocalDate expiryDate =
                startDate.plusMonths(plan.getDurationMonths());

        Subscription subscription =
                Subscription.builder()
                        .user(user)
                        .membershipPlan(plan)
                        .tier(tier)
                        .startDate(startDate)
                        .expiryDate(expiryDate)
                        .status("ACTIVE")
                        .build();

        return subscriptionRepository.save(subscription);
    }

    public Subscription upgradeTier(
            Long subscriptionId,
            Long tierId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Subscription not found"));

        MembershipTier tier =
                membershipTierRepository.findById(tierId)
                        .orElseThrow(() ->
                                new RuntimeException("Tier not found"));

        subscription.setTier(tier);

        return subscriptionRepository.save(subscription);
    }

    public Subscription downgradeTier(
            Long subscriptionId,
            Long tierId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Subscription not found"));

        MembershipTier tier =
                membershipTierRepository.findById(tierId)
                        .orElseThrow(() ->
                                new RuntimeException("Tier not found"));

        subscription.setTier(tier);

        return subscriptionRepository.save(subscription);
    }

    public Subscription cancelSubscription(
            Long subscriptionId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new RuntimeException("Subscription not found"));

        subscription.setStatus("CANCELLED");

        return subscriptionRepository.save(subscription);
    }

    public Subscription getSubscription(
            Long subscriptionId) {

        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new RuntimeException("Subscription not found"));
    }
}