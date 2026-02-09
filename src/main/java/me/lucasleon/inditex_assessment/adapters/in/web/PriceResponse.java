package me.lucasleon.inditex_assessment.adapters.in.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import me.lucasleon.inditex_assessment.domain.Price;

public record PriceResponse(
        long brandId,
        long productId,
        long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
    public static PriceResponse from(Price price) {
        return new PriceResponse(
                price.brandId(),
                price.productId(),
                price.priceList(),
                price.startDate(),
                price.endDate(),
                price.price(),
                price.currency()
        );
    }
}
