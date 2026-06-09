package com.firstclub.controller;

import com.firstclub.dto.SubscribeRequest;
import com.firstclub.dto.UpgradeTierRequest;
import com.firstclub.entity.Subscription;
import com.firstclub.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.firstclub.dto.MembershipResponse;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public Subscription subscribe(
            @RequestBody SubscribeRequest request) {

        return subscriptionService.subscribe(request);
    }

    @PutMapping("/upgrade")
    public Subscription upgradeTier(
            @RequestBody UpgradeTierRequest request) {

        return subscriptionService.upgradeTier(
                request.getSubscriptionId(),
                request.getTierId()
        );
    }

    @PutMapping("/downgrade")
    public Subscription downgradeTier(
            @RequestBody UpgradeTierRequest request) {

        return subscriptionService.downgradeTier(
                request.getSubscriptionId(),
                request.getTierId()
        );
    }

    @PutMapping("/cancel/{subscriptionId}")
    public Subscription cancelSubscription(
            @PathVariable Long subscriptionId) {

        return subscriptionService.cancelSubscription(subscriptionId);
    }

    @GetMapping("/{subscriptionId}")
    public MembershipResponse getSubscription(
            @PathVariable Long subscriptionId) {

        return subscriptionService.convertToResponse(
            subscriptionService.getSubscription(subscriptionId)
        );
    }
}
