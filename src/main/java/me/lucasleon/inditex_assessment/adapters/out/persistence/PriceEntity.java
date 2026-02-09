package me.lucasleon.inditex_assessment.adapters.out.persistence;

import me.lucasleon.inditex_assessment.domain.Price;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("prices")
public class PriceEntity {
    @Id
    private Long id;

    @Column("brand_id")
    private Long brandId;

    @Column("product_id")
    private Long productId;

    @Column("price_list")
    private Long priceList;

    @Column("priority")
    private Integer priority;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("price")
    private BigDecimal price;

    @Column("currency")
    private String currency;

    public PriceEntity(Long id, Long brandId, Long productId, Long priceList, Integer priority,
                       LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, String currency) {
        this.id = id;
        this.brandId = brandId;
        this.productId = productId;
        this.priceList = priceList;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPriceList() {
        return priceList;
    }

    public void setPriceList(Long priceList) {
        this.priceList = priceList;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Price toDomain() {
        return new Price(
                brandId,
                productId,
                priceList,
                priority,
                startDate,
                endDate,
                price,
                currency
        );
    }
}
