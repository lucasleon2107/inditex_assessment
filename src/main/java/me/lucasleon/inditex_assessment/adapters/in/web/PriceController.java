package me.lucasleon.inditex_assessment.adapters.in.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import me.lucasleon.inditex_assessment.application.port.PriceQueryUseCase;
import me.lucasleon.inditex_assessment.domain.PriceCriteria;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@Validated
public class PriceController {
    private final PriceQueryUseCase priceQueryUseCase;

    public PriceController(PriceQueryUseCase priceQueryUseCase) {
        this.priceQueryUseCase = priceQueryUseCase;
    }

    @GetMapping
    public Mono<PriceResponse> getApplicablePrice(
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @NotNull LocalDateTime applicationDate,
            @RequestParam("productId")
            @Positive long productId,
            @RequestParam("brandId")
            @Positive long brandId
    ) {
        PriceCriteria criteria = new PriceCriteria(brandId, productId, applicationDate);
        return priceQueryUseCase.findApplicablePrice(criteria)
                .map(PriceResponse::from);
    }
}
