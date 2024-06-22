package com.talent.authapi.entities;

//import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "No", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double feeRate;
    
    private boolean visible;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
}
