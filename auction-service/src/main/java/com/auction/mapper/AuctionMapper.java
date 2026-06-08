package com.auction.mapper;

import com.auction.document.AuctionDocument;
import com.auction.entity.Auction;
import org.springframework.stereotype.Component;

@Component
public class AuctionMapper {

    public AuctionDocument toDocument(
            Auction auction) {

        return AuctionDocument.builder()
                .id(auction.getId())
                .itemName(auction.getItemName())
                .sellerId(auction.getSellerId())
                .currentPrice(auction.getCurrentPrice())
                .status(auction.getStatus().name())
                .build();
    }
}