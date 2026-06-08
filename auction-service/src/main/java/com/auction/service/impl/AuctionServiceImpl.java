package com.auction.service.impl;

import com.auction.document.AuctionDocument;
import com.auction.dto.CreateAuctionRequest;
import com.auction.dto.PlaceBidRequest;
import com.auction.entity.Auction;
import com.auction.entity.AuctionStatus;
import com.auction.entity.Bid;
import com.auction.event.BidPlacedEvent;
import com.auction.exception.AuctionNotFoundException;
import com.auction.exception.InvalidBidException;
import com.auction.kafka.BidEventProducer;
import com.auction.mapper.AuctionMapper;
import com.auction.repository.AuctionRepository;
import com.auction.repository.BidRepository;
import com.auction.repository.elasticsearch.AuctionSearchRepository;
import com.auction.service.AuctionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.ConsoleHandler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final BidEventProducer bidEventProducer;
    private final AuctionMapper auctionMapper;
    private final AuctionSearchRepository auctionSearchRepository;

    @Override
    public Auction createAuction(CreateAuctionRequest request) {

        Auction auction = Auction.builder()
                .itemName(request.getItemName())
                .sellerId(request.getSellerId())
                .startPrice(request.getStartPrice())
                .currentPrice(request.getStartPrice())
                .endTime(
                        LocalDateTime.now()
                                .plusHours(request.getDurationHours())
                )
                .status(AuctionStatus.ACTIVE)
                .build();

        Auction savedAuction =
                auctionRepository.save(auction);

        auctionSearchRepository.save(
                auctionMapper.toDocument(savedAuction)
        );

        return savedAuction;
    }

    @Override
    @Cacheable(
            value = "auction",
            key = "#auctionId"
    )
    public Auction getAuction(Long auctionId) {

        System.out.println(
                "Fetching from PostgreSQL..."
        );

        return auctionRepository.findById(auctionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Auction not found"
                        ));
    }

    @Override
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public List<Bid> getAuctionBids(Long auctionId) {

        if (!auctionRepository.existsById(auctionId)) {
            throw new AuctionNotFoundException(auctionId);
        }

        return bidRepository.findByAuctionIdOrderByAmountDesc(
                auctionId
        );
    }

    @Transactional
    @CacheEvict(
            value = "auction",
            key = "#auctionId"
    )
    public Bid placeBid(Long auctionId,
                        PlaceBidRequest request) {

        Auction auction =
                auctionRepository
                        .lockAuction(auctionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Auction not found"
                                ));

        validateAuctionStatus(auction);

        validateBidAmount(
                auction,
                request.getAmount()
        );

        Bid bid = Bid.builder()
                .auctionId(auctionId)
                .bidderId(request.getBidderId())
                .amount(request.getAmount())
                .bidTime(LocalDateTime.now())
                .build();

        Bid savedBid = bidRepository.save(bid);

        bidEventProducer.publish(
                BidPlacedEvent.builder()
                        .auctionId(auctionId)
                        .bidderId(request.getBidderId())
                        .amount(request.getAmount())
                        .bidTime(LocalDateTime.now())
                        .build()
        );

        auction.setCurrentPrice(
                request.getAmount()
        );

        auctionRepository.save(auction);

        return savedBid;
    }

    private void validateAuctionStatus(
            Auction auction) {

        if (auction.getStatus() != AuctionStatus.ACTIVE) {

            throw new InvalidBidException(
                    "Auction is not active."
            );
        }

        if (LocalDateTime.now()
                .isAfter(auction.getEndTime())) {

            throw new InvalidBidException(
                    "Auction already ended."
            );
        }
    }

    private void validateBidAmount(
            Auction auction,
            java.math.BigDecimal bidAmount) {

        if (bidAmount.compareTo(
                auction.getCurrentPrice()) <= 0) {

            throw new InvalidBidException(
                    "Bid amount must be greater than current price."
            );
        }
    }

    @Override
    public List<AuctionDocument> search(
            String keyword) {

        return auctionSearchRepository
                .findByItemNameContainingIgnoreCase(keyword);
    }
}