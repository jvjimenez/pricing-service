package com.jvjimenez.pricing.infrastructure.adapter.out.persistence.repository;


import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.out.PricePersistencePort;
import com.jvjimenez.pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public class PriceJpaRepository implements PricePersistencePort {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<Price> findByBrandAndProductAndDate(Long brandId, Long productId, Instant searchDate) {
        TypedQuery<PriceEntity> query = em.createQuery(
                "SELECT p FROM PriceEntity p " +
                        "WHERE p.brandId = :brandId " +
                        "AND p.productId = :productId " +
                        "AND p.startDate <= :searchDate " +
                        "AND p.endDate >= :searchDate " +
                        "ORDER BY p.priority DESC",
                PriceEntity.class
        );

        query.setParameter("brandId", brandId);
        query.setParameter("productId", productId);
        query.setParameter("searchDate", searchDate);
        query.setMaxResults(1);

        return query.getResultList()
                .stream()
                .findFirst()
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getProductId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getApplicableRate(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
