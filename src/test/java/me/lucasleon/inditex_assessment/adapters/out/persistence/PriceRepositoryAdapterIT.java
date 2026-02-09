package me.lucasleon.inditex_assessment.adapters.out.persistence;

import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PriceRepositoryAdapterIT {

    @Autowired
    private PriceRepositoryAdapter repository;

    @Test
    void returnsHighestPriorityWhenOverlapping() {
        PriceCriteria criteria = new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0));

        StepVerifier.create(repository.findApplicablePrice(criteria))
                .assertNext(price -> {
                    assertThat(price.priceList()).isEqualTo(2L);
                    assertThat(price.price()).isEqualByComparingTo("25.45");
                })
                .verifyComplete();
    }
}
