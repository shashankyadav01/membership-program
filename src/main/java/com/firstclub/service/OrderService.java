package com.firstclub.service;

import com.firstclub.entity.*;
import com.firstclub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TierService tierService;

    public Order createOrder(Order order) {

        Order savedOrder =
                orderRepository.save(order);

        User user = order.getUser();

        user.setTotalOrders(
                user.getTotalOrders() + 1
        );

        user.setMonthlyOrderValue(
                user.getMonthlyOrderValue()
                        + order.getOrderValue()
        );

        userRepository.save(user);

        MembershipTier tier =
                tierService.evaluateTier(user);

        if (tier != null) {

            subscriptionRepository
                    .findByUser(user)
                    .ifPresent(subscription -> {

                        subscription.setTier(tier);

                        subscriptionRepository
                                .save(subscription);
                    });
        }

        return savedOrder;
    }
}