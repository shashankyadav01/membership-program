package com.firstclub.dto;

import lombok.Data;

@Data
public class UpgradeTierRequest {

    private Long subscriptionId;
    private Long tierId;
}