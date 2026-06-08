package com.auction.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "auctions")
public class AuctionDocument {

    @Id
    private Long id;

    private String itemName;

    private Long sellerId;

    private BigDecimal currentPrice;

    private String status;
}