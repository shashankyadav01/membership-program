package com.firstclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_tiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tierName;

    private Double discountPercentage;

    private Boolean freeDelivery;

    private Boolean prioritySupport;

    private Integer minimumOrders;

    private Double minimumOrderValue;

    private String cohort;
}