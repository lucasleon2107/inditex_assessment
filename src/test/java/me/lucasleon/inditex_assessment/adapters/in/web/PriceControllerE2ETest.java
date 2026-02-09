package me.lucasleon.inditex_assessment.adapters.in.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerE2ETest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUpClient() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void test1_2020_06_14_10_00() {
        assertPrice("2020-06-14T10:00:00", 1, 35455, 1, 35.50);
    }

    @Test
    void test2_2020_06_14_16_00() {
        assertPrice("2020-06-14T16:00:00", 1, 35455, 2, 25.45);
    }

    @Test
    void test3_2020_06_14_21_00() {
        assertPrice("2020-06-14T21:00:00", 1, 35455, 1, 35.50);
    }

    @Test
    void test4_2020_06_15_10_00() {
        assertPrice("2020-06-15T10:00:00", 1, 35455, 3, 30.50);
    }

    @Test
    void test5_2020_06_16_21_00() {
        assertPrice("2020-06-16T21:00:00", 1, 35455, 4, 38.95);
    }

    private void assertPrice(String applicationDate, long brandId, long productId, long expectedPriceList, double expectedPrice) {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/prices")
                        .queryParam("applicationDate", applicationDate)
                        .queryParam("productId", productId)
                        .queryParam("brandId", brandId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.brandId").isEqualTo((int) brandId)
                .jsonPath("$.productId").isEqualTo((int) productId)
                .jsonPath("$.priceList").isEqualTo((int) expectedPriceList)
                .jsonPath("$.price").isEqualTo(expectedPrice)
                .jsonPath("$.currency").isEqualTo("EUR");
    }
}
