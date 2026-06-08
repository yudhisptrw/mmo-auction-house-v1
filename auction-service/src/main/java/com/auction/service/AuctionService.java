package com.auction.service;

import com.auction.document.AuctionDocument;
import com.auction.dto.CreateAuctionRequest;
import com.auction.dto.PlaceBidRequest;
import com.auction.entity.Auction;
import com.auction.entity.Bid;
import com.auction.kafka.BidEventProducer;

import java.util.List;

public interface AuctionService {

    Auction createAuction(CreateAuctionRequest request);

    Auction getAuction(Long auctionId);

    List<Auction> getAllAuctions();

    List<Bid> getAuctionBids(Long auctionId);

    Bid placeBid(Long auctionId,
                 PlaceBidRequest request);

    List<AuctionDocument> search(String keyword);


}