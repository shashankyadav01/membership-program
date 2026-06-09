package com.firstclub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipResponse {

    private Long subscriptionId;

    private String userName;

    private String planName;

    private String tierName;

    private String status;

    private String expiryDate;
}