package me.lucasleon.inditex_assessment.adapters.out.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceReactiveRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
            SELECT brand_id, product_id, price_list, priority, start_date, end_date, price, currency
            FROM prices
            WHERE brand_id = :brandId
              AND product_id = :productId
              AND :applicationDate BETWEEN start_date AND end_date
            ORDER BY priority DESC, start_date DESC, id DESC
            LIMIT 1
            """)
    Mono<PriceEntity> findApplicablePrice(
            @Param("brandId") long brandId,
            @Param("productId") long productId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}
