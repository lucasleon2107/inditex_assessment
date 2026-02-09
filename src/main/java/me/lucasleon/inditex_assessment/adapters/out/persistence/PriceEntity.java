package me.lucasleon.inditex_assessment.adapters.out.persistence;

import me.lucasleon.inditex_assessment.domain.Price;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("prices")
public record PriceEntity(
        @Id Long id,
        @Column("brand_id") Long brandId,
        @Column("product_id") Long productId,
        @Column("price_list") Long priceList,
        @Column("priority") Integer priority,
        @Column("start_date") LocalDateTime startDate,
        @Column("end_date") LocalDateTime endDate,
        @Column("price") BigDecimal price,
        @Column("currency") String currency
) {
    public Price toDomain() {
        return new Price(
                brandId,
                productId,
                priceList,
                priority,
                startDate,
                endDate,
                price,
                currency
        );
    }
}
