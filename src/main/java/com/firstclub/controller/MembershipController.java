package com.firstclub.controller;

import com.firstclub.entity.MembershipPlan;
import com.firstclub.entity.MembershipTier;
import com.firstclub.service.MembershipService;
import com.firstclub.service.TierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final TierService tierService;

    @GetMapping("/plans")
    public List<MembershipPlan> getPlans() {
        return membershipService.getAllPlans();
    }

    @PostMapping("/plans")
    public MembershipPlan createPlan(@RequestBody MembershipPlan plan) {
        return membershipService.createPlan(plan);
    }

    @GetMapping("/tiers")
    public List<MembershipTier> getTiers() {
        return tierService.getAllTiers();
    }

    @PostMapping("/tiers")
    public MembershipTier createTier(@RequestBody MembershipTier tier) {
        return tierService.createTier(tier);
    }
}