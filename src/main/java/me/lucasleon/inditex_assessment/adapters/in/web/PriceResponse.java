package me.lucasleon.inditex_assessment.adapters.in.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        long brandId,
        long productId,
        long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}
