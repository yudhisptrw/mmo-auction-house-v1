package com.auction.kafka;

import com.auction.event.BidPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(BidPlacedEvent event) {

        kafkaTemplate.send(
                "bid-events",
                event
        );
    }
}
