package com.auction.repository;

import com.auction.entity.Auction;
import com.auction.entity.AuctionStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository
        extends JpaRepository<Auction, Long> {

    List<Auction> findByStatus(
            AuctionStatus status);

    List<Auction> findBySellerId(
            Long sellerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT a
            FROM Auction a
            WHERE a.id = :auctionId
            """)
    Optional<Auction> lockAuction(
            @Param("auctionId")
            Long auctionId);
}