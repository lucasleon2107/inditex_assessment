package me.lucasleon.inditex_assessment.adapters.in.web;

import me.lucasleon.inditex_assessment.application.PriceNotFoundException;
import me.lucasleon.inditex_assessment.application.port.PriceQueryUseCase;
import me.lucasleon.inditex_assessment.domain.Price;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Test
    void returnsApplicablePrice() {
        PriceQueryUseCase priceQueryUseCase = mock(PriceQueryUseCase.class);
        WebTestClient webTestClient = buildClient(priceQueryUseCase);
        Price price = new Price(
                1L,
                35455L,
                1L,
                0,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("35.50"),
                "EUR"
        );

        when(priceQueryUseCase.findApplicablePrice(any(PriceCriteria.class))).thenReturn(Mono.just(price));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/prices")
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.brandId").isEqualTo(1)
                .jsonPath("$.productId").isEqualTo(35455)
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.price").isEqualTo(35.50)
                .jsonPath("$.currency").isEqualTo("EUR");
    }

    @Test
    void returnsNotFoundWhenPriceIsMissing() {
        PriceQueryUseCase priceQueryUseCase = mock(PriceQueryUseCase.class);
        WebTestClient webTestClient = buildClient(priceQueryUseCase);

        when(priceQueryUseCase.findApplicablePrice(any(PriceCriteria.class)))
                .thenReturn(Mono.error(new PriceNotFoundException(
                        new PriceCriteria(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0))
                )));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/prices")
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.message").value(message -> assertThat(message.toString()).contains("No price found"));
    }

    private WebTestClient buildClient(PriceQueryUseCase priceQueryUseCase) {
        PriceController controller = new PriceController(priceQueryUseCase);
        return WebTestClient.bindToController(controller)
                .controllerAdvice(new PriceExceptionHandler())
                .build();
    }
}
