package com.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            AuctionNotFoundException.class
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleAuctionNotFound(
            Exception ex) {

        return Map.of(
                "timestamp",
                LocalDateTime.now(),
                "message",
                ex.getMessage()
        );
    }

    @ExceptionHandler(
            InvalidBidException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidBid(
            Exception ex) {

        return Map.of(
                "timestamp",
                LocalDateTime.now(),
                "message",
                ex.getMessage()
        );
    }
}