package com.auction.controller;

import com.auction.document.AuctionDocument;
import com.auction.dto.CreateAuctionRequest;
import com.auction.dto.PlaceBidRequest;
import com.auction.entity.Auction;
import com.auction.entity.Bid;
import com.auction.repository.elasticsearch.AuctionSearchRepository;
import com.auction.service.AuctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionSearchRepository auctionSearchRepository;

    @PostMapping("/createauction")
    public Auction createAuction(
            @Valid @RequestBody
            CreateAuctionRequest request) {

        return auctionService.createAuction(
                request
        );
    }

    @GetMapping("/getallauction")
    public List<Auction> getAllAuctions() {

        return auctionService.getAllAuctions();
    }

    @GetMapping("/{auctionId}/getauctionbyid")
    public Auction getAuction(
            @PathVariable Long auctionId) {

        return auctionService.getAuction(
                auctionId
        );
    }

    @GetMapping("/{auctionId}/getallbidsbyauctionid")
    public List<Bid> getAuctionBids(
            @PathVariable Long auctionId) {

        return auctionService.getAuctionBids(
                auctionId
        );
    }

    @PostMapping("/{auctionId}/placebid")
    public Bid placeBid(
            @PathVariable Long auctionId,
            @Valid @RequestBody
            PlaceBidRequest request) {

        return auctionService.placeBid(
                auctionId,
                request
        );
    }

    @GetMapping("/search")
    public List<AuctionDocument> search(
            @RequestParam String q) {

        return auctionService.search(q);
    }
}