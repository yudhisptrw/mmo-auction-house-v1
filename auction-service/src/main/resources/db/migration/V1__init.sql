CREATE TABLE mst_auction
(
    id BIGSERIAL PRIMARY KEY,

    item_name VARCHAR(200) NOT NULL,

    seller_id BIGINT NOT NULL,

    start_price NUMERIC(18,2) NOT NULL,

    current_price NUMERIC(18,2) NOT NULL,

    end_time TIMESTAMP NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_date TIMESTAMP,

    updated_date TIMESTAMP
);

CREATE TABLE trx_bid
(
    id BIGSERIAL PRIMARY KEY,

    auction_id BIGINT NOT NULL,

    bidder_id BIGINT NOT NULL,

    amount NUMERIC(18,2) NOT NULL,

    bid_time TIMESTAMP NOT NULL,

    created_date TIMESTAMP,

    updated_date TIMESTAMP
);

CREATE INDEX idx_bid_auction
    ON trx_bid(auction_id);

CREATE INDEX idx_auction_status
    ON mst_auction(status);