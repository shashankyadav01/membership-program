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

    private final MembershipTierRepository membershipTierRepository;

    public List<MembershipTier> getAllTiers() {
        return membershipTierRepository.findAll();
    }

    public MembershipTier createTier(
            MembershipTier tier) {

        return membershipTierRepository.save(tier);
    }

    public MembershipTier evaluateTier(User user) {

        List<MembershipTier> tiers =
                membershipTierRepository.findAll();

        MembershipTier selectedTier = null;

        for (MembershipTier tier : tiers) {

            if (user.getTotalOrders()
                    >= tier.getMinimumOrders()
                    &&
                    user.getMonthlyOrderValue()
                            >= tier.getMinimumOrderValue()) {

                selectedTier = tier;
            }
        }

        return selectedTier;
    }
}