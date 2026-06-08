package com.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AuctionResponse {

    private Long id;

    private String itemName;

    private Long sellerId;

    private BigDecimal currentPrice;

    private String status;

    private LocalDateTime endTime;

}