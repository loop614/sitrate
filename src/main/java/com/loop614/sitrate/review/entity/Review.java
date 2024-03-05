package com.loop614.sitrate.review.entity;

import java.math.BigDecimal;

import com.loop614.sitrate.product.entity.Product;

import jakarta.persistence.*;

@Entity
@Table(name = "REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(name="PRODUCT_ID", insertable=false, updatable=false)
    private Long productId;

    @Column(name = "REVIEWER", nullable = false)
    private String reviewer;

    @Column(name = "TEXT", columnDefinition = "TEXT")
    private String text;

    @Column(name = "RATING", nullable = false, scale = 15, precision = 30)
    private BigDecimal rating;

    public Review(String reviewer, String text, BigDecimal rating)
    {
        this.reviewer = reviewer;
        this.text = text;
        this.rating = rating;
    }

    public Long getId() {
        return this.id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
