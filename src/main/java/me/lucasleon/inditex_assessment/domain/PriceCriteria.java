package me.lucasleon.inditex_assessment.domain;

import java.time.LocalDateTime;

public record PriceCriteria(
        long brandId,
        long productId,
        LocalDateTime applicationDate
) {
}
