package me.lucasleon.inditex_assessment.application.port;

import me.lucasleon.inditex_assessment.domain.Price;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import reactor.core.publisher.Mono;

public interface PriceQueryUseCase {
    Mono<Price> findApplicablePrice(PriceCriteria criteria);
}
