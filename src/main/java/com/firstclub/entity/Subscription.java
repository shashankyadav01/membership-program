package com.firstclub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MembershipPlan membershipPlan;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private MembershipTier tier;

    private LocalDate startDate;

    private LocalDate expiryDate;

    private String status;

    @Version
    private Long version;
}