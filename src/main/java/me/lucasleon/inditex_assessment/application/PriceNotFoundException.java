package me.lucasleon.inditex_assessment.application;

import me.lucasleon.inditex_assessment.domain.PriceCriteria;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(PriceCriteria criteria) {
        super("No price found for brandId=%d, productId=%d, applicationDate=%s"
                .formatted(criteria.brandId(), criteria.productId(), criteria.applicationDate()));
    }
}
