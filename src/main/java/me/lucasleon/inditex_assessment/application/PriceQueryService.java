package me.lucasleon.inditex_assessment.application;

import me.lucasleon.inditex_assessment.application.port.PriceQueryUseCase;
import me.lucasleon.inditex_assessment.domain.Price;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import me.lucasleon.inditex_assessment.domain.port.PriceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PriceQueryService implements PriceQueryUseCase {
    private final PriceRepository priceRepository;

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Mono<Price> findApplicablePrice(PriceCriteria criteria) {
        return priceRepository.findApplicablePrice(criteria)
                .switchIfEmpty(Mono.error(new PriceNotFoundException(criteria)));
    }
}
