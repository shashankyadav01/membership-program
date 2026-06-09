package com.firstclub.service;

import com.firstclub.entity.MembershipPlan;
import com.firstclub.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipPlanRepository membershipPlanRepository;

    public List<MembershipPlan> getAllPlans() {
        return membershipPlanRepository.findAll();
    }

    public MembershipPlan getPlan(Long id) {
        return membershipPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    public MembershipPlan createPlan(MembershipPlan plan) {
        return membershipPlanRepository.save(plan);
    }

    public void deletePlan(Long id) {
        membershipPlanRepository.deleteById(id);
    }
}