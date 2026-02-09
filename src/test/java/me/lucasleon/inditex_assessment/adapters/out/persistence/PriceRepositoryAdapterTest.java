package me.lucasleon.inditex_assessment.adapters.out.persistence;

import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceRepositoryAdapterTest {

    @Test
    void mapsEntityToDomainPrice() {
        PriceReactiveRepository reactiveRepository = mock(PriceReactiveRepository.class);
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(reactiveRepository);
        PriceCriteria criteria = new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        PriceEntity entity = new PriceEntity(
                1L,
                1L,
                35455L,
                1L,
                0,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("35.50"),
                "EUR"
        );

        when(reactiveRepository.findApplicablePrice(criteria.brandId(), criteria.productId(), criteria.applicationDate()))
                .thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.findApplicablePrice(criteria))
                .assertNext(price -> {
                    assertThat(price.brandId()).isEqualTo(1L);
                    assertThat(price.productId()).isEqualTo(35455L);
                    assertThat(price.priceList()).isEqualTo(1L);
                    assertThat(price.price()).isEqualByComparingTo("35.50");
                    assertThat(price.currency()).isEqualTo("EUR");
                })
                .verifyComplete();
    }

    @Test
    void returnsEmptyWhenNoEntityFound() {
        PriceReactiveRepository reactiveRepository = mock(PriceReactiveRepository.class);
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(reactiveRepository);
        PriceCriteria criteria = new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        when(reactiveRepository.findApplicablePrice(criteria.brandId(), criteria.productId(), criteria.applicationDate()))
                .thenReturn(Mono.empty());

        StepVerifier.create(adapter.findApplicablePrice(criteria))
                .verifyComplete();
    }
}
