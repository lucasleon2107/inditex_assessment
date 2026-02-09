package me.lucasleon.inditex_assessment.application;

import me.lucasleon.inditex_assessment.domain.Price;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import me.lucasleon.inditex_assessment.domain.port.PriceRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class PriceQueryServiceTest {

    @Test
    void returnsPriceWhenRepositoryMatches() {
        PriceCriteria criteria = new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));
        Price expected = new Price(1L, 35455L, 1L, 0,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("35.50"),
                "EUR");

        PriceRepository repository = query -> Mono.just(expected);
        PriceQueryService service = new PriceQueryService(repository);

        StepVerifier.create(service.findApplicablePrice(criteria))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void throwsWhenNoPriceFound() {
        PriceCriteria criteria = new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));
        PriceRepository repository = query -> Mono.empty();
        PriceQueryService service = new PriceQueryService(repository);

        StepVerifier.create(service.findApplicablePrice(criteria))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(PriceNotFoundException.class);
                    assertThat(ex.getMessage()).contains("No price found");
                })
                .verify();
    }
}
