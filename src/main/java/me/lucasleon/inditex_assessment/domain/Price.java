package me.lucasleon.inditex_assessment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        long brandId,
        long productId,
        long priceList,
        int priority,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}
