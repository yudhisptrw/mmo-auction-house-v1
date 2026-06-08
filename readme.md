# MMO Auction House

A modern auction platform backend built with Spring Boot using PostgreSQL, Redis, Kafka, and Elasticsearch.

## Overview

MMO Auction House is a backend service that simulates an online game marketplace where users can create auctions, place bids, search auctions, and process auction events in real time.

This project demonstrates a microservice-ready architecture using:

- REST API
- PostgreSQL
- Redis Cache
- Apache Kafka
- Elasticsearch
- Flyway Database Migration

---

# Architecture

```text
                   +------------------+
                   |     Swagger UI   |
                   +--------+---------+
                            |
                            v
                +-----------------------+
                |    Auction Service    |
                |    Spring Boot 3      |
                +-----+-----+-----+-----+
                      |     |     |
                      |     |     |
                      v     v     v

               PostgreSQL Redis Kafka
                 (Neon)   Cloud Aiven

                                |
                                v

                        Elasticsearch
                           Cloud
```

---

# Technology Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| ORM | Spring Data JPA |
| Database | PostgreSQL |
| Cache | Redis Cloud |
| Messaging | Apache Kafka |
| Search Engine | Elasticsearch |
| Database Migration | Flyway |
| API Documentation | Swagger OpenAPI |
| Build Tool | Maven |
| Validation | Jakarta Validation |
| Boilerplate Reduction | Lombok |

---

# Features

## Auction Management

- Create Auction
- Get Auction By ID
- Get All Auctions
- Auction Status Management

## Bidding System

- Place Bid
- Bid Validation
- Current Highest Price Tracking
- Bid History

## Redis Cache

- Cache Auction Detail
- Faster Read Performance
- Cache Eviction After New Bid

## Kafka Event Streaming

- Publish BidPlacedEvent
- Consume BidPlacedEvent
- Event Driven Architecture

## Elasticsearch Search

- Auction Indexing
- Search By Item Name
- Full Text Search

## Exception Handling

- Global Exception Handler
- Custom Business Exceptions
- Standardized Error Response

---

# Database Schema

## Auction

| Column | Type |
|----------|----------|
| id | BIGINT |
| item_name | VARCHAR |
| seller_id | BIGINT |
| start_price | DECIMAL |
| current_price | DECIMAL |
| end_time | TIMESTAMP |
| status | VARCHAR |

---

## Bid

| Column | Type |
|----------|----------|
| id | BIGINT |
| auction_id | BIGINT |
| bidder_id | BIGINT |
| amount | DECIMAL |
| bid_time | TIMESTAMP |

---

# API Endpoints

## Create Auction

```http
POST /api/auctions/createauction
```

### Request

```json
{
  "itemName": "Legendary Sword",
  "sellerId": 1,
  "startPrice": 1000,
  "durationHours": 24
}
```

---

## Get All Auctions

```http
GET /api/auctions/getallauction
```

---

## Get Auction By ID

```http
GET /api/auctions/{auctionId}/getauctionbyid
```

---

## Get Auction Bids

```http
GET /api/auctions/{auctionId}/getallbidsbyauctionid
```

---

## Place Bid

```http
POST /api/auctions/{auctionId}/placebid
```

### Request

```json
{
  "bidderId": 2,
  "amount": 2000
}
```

---

## Search Auction

```http
GET /api/auctions/search?q=sword
```

---

# Event Flow

## Bid Placement Flow

```text
User
 |
 | POST /placebid
 |
 v
Auction Service
 |
 +--> Validate Bid
 |
 +--> Save Bid (PostgreSQL)
 |
 +--> Update Current Price
 |
 +--> Evict Redis Cache
 |
 +--> Publish Kafka Event
 |
 v
Kafka Topic (bid-events)
 |
 v
Kafka Consumer
 |
 v
Event Processing
```

---

# Search Flow

```text
Create Auction
      |
      v
PostgreSQL
      |
      v
Elasticsearch Index
      |
      v
Search Endpoint
      |
      v
Search Result
```

---

# Cloud Services Used

## PostgreSQL

Provider:

- Neon

Purpose:

- Primary Database

---

## Redis

Provider:

- Redis Cloud

Purpose:

- Application Cache

---

## Kafka

Provider:

- Aiven

Purpose:

- Event Streaming

---

## Elasticsearch

Provider:

- Elastic Cloud Serverless

Purpose:

- Full Text Search

---

# Running Locally

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL
- Redis
- Kafka
- Elasticsearch

---

## Build

```bash
mvn clean install
```

---

## Run

```bash
mvn spring-boot:run
```

---

## Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

---

# Configuration

Copy folder cert to auction-service
and
replace content from dev to:

```text
application.yml
```


Contact dev for credentials.


---

# Future Improvements

- JWT Authentication
- Spring Security
- User Management
- Auction Scheduler
- WebSocket Real-Time Bidding
- Docker Support
- Docker Compose
- Kubernetes Deployment
- CI/CD Pipeline
- Multi-Service Architecture

---

# Project Highlights

This project demonstrates:

- Clean Architecture
- REST API Design
- Database Modeling
- Redis Caching
- Kafka Event Streaming
- Elasticsearch Integration
- Exception Handling
- Validation
- Cloud Service Integration

---

# Author

Yudhistira Putra Wardhana

Backend Engineer

Java • Spring Boot • PostgreSQL • Redis • Kafka • Elasticsearch