package com.jvjimenez.pricing.infrastructure.adapter.out.persistence.repository;


import com.jvjimenez.pricing.domain.model.Price;
import com.jvjimenez.pricing.domain.port.out.PricePersistencePort;
import com.jvjimenez.pricing.infrastructure.adapter.out.persistence.entity.PriceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class PriceJpaRepository implements PricePersistencePort {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Price> findByBrandAndProductAndDate(Long brandId, Long productId, Instant searchDate) {
        try {
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

            PriceEntity entity = query.getSingleResult();
            return Optional.of(toDomain(entity));
        } catch (NoResultException e) {
            return Optional.empty();
        }
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

