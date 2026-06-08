package com.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAuctionRequest {

    @Schema(example = "Dragon Sword +10")
    @NotBlank
    private String itemName;

    @Schema(example = "1001")
    @NotNull
    private Long sellerId;

    @Schema(example = "500000")
    @DecimalMin(value = "0.01")
    @NotNull
    private BigDecimal startPrice;

    @Schema(example = "24")
    @Min(1)
    @Max(168)
    @NotNull
    private Integer durationHours;

}