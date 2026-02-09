package me.lucasleon.inditex_assessment.domain.port;

import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import me.lucasleon.inditex_assessment.domain.Price;

import reactor.core.publisher.Mono;

public interface PriceRepository {
    Mono<Price> findApplicablePrice(PriceCriteria criteria);
}
