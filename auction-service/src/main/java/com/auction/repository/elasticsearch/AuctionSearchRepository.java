package com.auction.repository.elasticsearch;

import com.auction.document.*;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AuctionSearchRepository
        extends ElasticsearchRepository<
                AuctionDocument,
                Long> {

    List<AuctionDocument>
    findByItemNameContainingIgnoreCase(
            String itemName);
}
