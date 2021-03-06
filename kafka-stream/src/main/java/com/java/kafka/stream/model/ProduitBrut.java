package com.java.kafka.stream.model;

import java.math.BigDecimal;

public class ProduitBrut {

    private Long id;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProduitBrut{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
