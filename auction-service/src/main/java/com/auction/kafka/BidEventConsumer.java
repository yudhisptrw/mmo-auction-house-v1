package com.auction.kafka;

import com.auction.event.BidPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BidEventConsumer {

    @KafkaListener(
            topics = "bid-events",
            groupId = "auction-group"
    )
    public void consume(
            BidPlacedEvent event) {

        log.info(
                "EVENT RECEIVED {}",
                event
        );
    }
}