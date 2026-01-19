# Product prices service

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Configuration](#configuration)
- [Database Schema](#database-schema)
- [API Documentation](#api-documentation)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Service](#running-the-service)
- [Testing](#testing)
- [Versioning](#versioning)
- [About the author](#about-the-autor)

---

## Overview

This technical test is a Spring Boot microservice built with **Java 21 and Spring Boot** that provides an API for
querying product prices based on brand, product, and effective date. It follows a **hexagonal architecture** with
clearly separated layers for domain, application, and infrastructure
via a REST API.

* **Stack:** Java 21, Spring Boot 3.5.9, H2 database, Maven, MapStruct, Jakarta Validation, JUnit 5, Mockito, SpringDoc
* **Architecture:** Hexagonal, SOLID principles and DDD.
* **Documentation:** Swagger UI (SpringDoc OpenAPI).

---

## Architecture

- **Domain Layer:** Contains business entities and inbound/outbound port interfaces.
    * Outbound port - `PricePersistencePort`: Data persistence for querying the database.
- **Application Layer:** Implements use cases and DTOs.
    * `GetPriceUseCase`: Use case for query product prices.
- **Infrastructure Layer:**
    * Handles persistence (`PriceJPARepository` with ``H2`` database), 
    * REST adapter (`PriceController`), 
    * and DTO mappings.

**Simplified diagram:**

```
Client --> Controller --> Use Cases --> Domain Entities --> Repository/DB
```

---

## Features

This service implements a very simple pricing logic:

- Retrieve the active price for a product by brand and date.
- Accepts `brandId`, `productId`, and `searchDate` as inputs.
- Returns the applicable price, start/end dates, currency, and final price.
- Selects the price with highest priority if multiple rates apply.
- Unit and integration tests.
- Input validation for all REST endpoints.
- DTOs and mapping with MapStruct.

---

## Configuration

All configurations are in `application.properties`.

---

## Database Schema

The service uses `H2` in-memory database, with table `price` to store product prices. The schema is defined as follows:

```sql
CREATE TABLE price
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id        BIGINT         NOT NULL,
    product_id      BIGINT         NOT NULL,
    applicable_rate BIGINT         NOT NULL,
    priority        INT            NOT NULL,
    start_date      TIMESTAMP      NOT NULL,
    end_date        TIMESTAMP      NOT NULL,
    price           DECIMAL(10, 2) NOT NULL,
    curr            VARCHAR(3)     NOT NULL
);

CREATE INDEX idx_brand_product_dates_priority
    ON price (brand_id, product_id, start_date, end_date, priority DESC);
```

This index optimizes queries by brand, product, date range, and priority.

---

## API Documentation

Swagger UI is available for exploring the API here: http://localhost:8080/swagger-ui.html

| Endpoint     | Method | Description                                                   |
|--------------|--------|---------------------------------------------------------------|
| `/api/price` | GET    | Returns the price for a given brand, product, and search date |

**Query Parameters:**

- `brandId` (Long, required)
- `productId` (Long, required)
- `searchDate` (ISO 8601, required)

**Response Example:**

```json
{
  "brandId": 35455,
  "productId": 1,
  "applicableRate": 1,
  "price": 25.45,
  "currency": "EUR",
  "startDate": "2020-06-14T15:00:00Z",
  "endDate": "2020-06-14T18:30:00Z"
}
```

---
## Getting Started

### Prerequisites

- Java 21
- Maven

### Installation

```bash
git clone https://github.com/jvvjimenez/pricing-service.git
cd pricing-service
mvn clean install
```

### Running the Service

#### Using Maven:

```bash
mvn spring-boot:run
```

---
## Testing

Tests cover examples for Unit Tests, integration Test and DTO mappings Tests.

- Run all tests:

```shell
mvn clean verify
```

### Coverage

- Use IntelliJ "Run with Coverage".

## Versioning

This repository uses automatic semantic versioning. Every time a pull request is merged into ``main``, a GitHub Actions
workflow automatically increments the version number following the ``MAJOR.MINOR.PATCH`` scheme.

* **MAJOR**: breaking changes

* **MINOR**: new features, backwards compatible

* **PATCH**: bug fixes

---

## About the autor

**Project Maintainer:** Juan Vicente Jiménez

**GitHub:** [https://github.com/jvjimenez/pricing-service](https://github.com/jvjimenez/pricing-service)

