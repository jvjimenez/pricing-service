package com.jvjimenez.pricing.infrastructure.persistence.jpa.repository;


import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.secondary.PricePersistenceService;
import com.jvjimenez.pricing.infrastructure.persistence.jpa.entity.PriceDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class PriceRepository implements PricePersistenceService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Price> findByBrandAndProductAndDate(Long brandId, Long productId, Instant searchDate) {

        TypedQuery<PriceDB> query = em.createQuery(
                "SELECT p FROM PriceDB p " +
                        "WHERE p.brandId = :brandId " +
                        "AND p.productId = :productId " +
                        "AND p.startDate <= :searchDate " +
                        "AND p.endDate >= :searchDate " +
                        "ORDER BY p.priority DESC",
                PriceDB.class
        );

        query.setParameter("brandId", brandId);
        query.setParameter("productId", productId);
        query.setParameter("searchDate", searchDate);
        query.setMaxResults(1);

        return query.getResultStream()
                .map(this::toDomain)
                .findFirst();
    }

    private Price toDomain(PriceDB entity) {
        return new Price(
                entity.getBrandId(),
                entity.getProductId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getApplicableRate(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurr()
        );
    }
}

