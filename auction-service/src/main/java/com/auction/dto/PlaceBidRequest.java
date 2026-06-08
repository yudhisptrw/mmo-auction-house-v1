package com.auction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceBidRequest {

    @NotNull
    private Long bidderId;

    @NotNull
    private BigDecimal amount;

}