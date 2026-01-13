# pricing-service


This technical test is a Spring Boot microservice with hexagonal architecture that provides product pricing information via a REST API.

### Overview

This service implements a very simple pricing logic:

- Accepts productId,  brandId and searchDate as inputs.

- Returns the applicable price, start/end dates, currency, and final price.

- Selects the price with highest priority if multiple rates apply.

- Swagger UI is available for exploring the API: http://localhost:8080/swagger-ui.html

  - Access the API:``GET http://localhost:8080/api/price?searchDate=2020-06-14T16:00:00Z&productId=35455&brandId=1``


## Requirements

* JDK 21
* Maven

## Running the Service

- Clone the repository:

 ```shell
git clone git@github.com:jvjimenez/pricing.git
cd pricing
```
 
- Build and run:


```shell
mvn clean spring-boot:run
```

## Testing

Tests cover examples of domain logic, controller and dto mappings.

- Run all tests:
```shell
mvn clean verify
```
## Versioning 

Included example workflow that build, test and tag a new version when a PR is merged into main.

## Notes

- As required, the service uses H2 in-memory for tests and initial data is loaded via data.sql.
- All domain and API models use BigDecimal for prices and Instant (UTC) for dates to ensure precision.
- Regarding the database table
  - Added column *cur* for currency.
  - The name of the *price_list* column has been renamed to *applicable_rate* to make it more descriptive.

