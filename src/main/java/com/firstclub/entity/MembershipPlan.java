package com.firstclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planName;

    private Integer durationMonths;

    private Double price;

    private Double discountPercentage;

    private Boolean freeDelivery;

    private Boolean prioritySupport;
}