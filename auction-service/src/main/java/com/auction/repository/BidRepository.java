package com.auction.repository;

import com.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByAuctionId(Long auctionId);

    List<Bid> findByAuctionIdOrderByAmountDesc(Long auctionId);

    Optional<Bid> findTopByAuctionIdOrderByAmountDesc(Long auctionId);

}