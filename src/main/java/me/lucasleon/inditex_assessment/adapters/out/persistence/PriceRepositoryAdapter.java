package me.lucasleon.inditex_assessment.adapters.out.persistence;

import me.lucasleon.inditex_assessment.domain.Price;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import me.lucasleon.inditex_assessment.domain.port.PriceRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PriceRepositoryAdapter implements PriceRepository {
    private final PriceReactiveRepository repository;

    public PriceRepositoryAdapter(PriceReactiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Price> findApplicablePrice(PriceCriteria criteria) {
        return repository.findApplicablePrice(criteria.brandId(), criteria.productId(), criteria.applicationDate())
                .map(PriceEntity::toDomain);
    }
}
