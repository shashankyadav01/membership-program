package com.firstclub.dto;

import lombok.Data;

@Data
public class SubscribeRequest {

    private Long userId;
    private Long planId;
    private Long tierId;
}