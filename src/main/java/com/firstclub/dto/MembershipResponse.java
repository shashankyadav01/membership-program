package com.firstclub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipResponse {

    private Long userId;
    private String userName;
    private String planName;
    private String tierName;
    private String status;
    private String expiryDate;
}