package com.talent.authapi.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "like_list")
public class LikeList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sn", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "like_list_product",
            joinColumns = @JoinColumn(name = "like_list_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "total_fee", nullable = false)
    private double totalFee;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    public LikeList() {
    }

    public LikeList(User user, List<Product> products, int numberOfProducts, String account, double totalFee, double totalAmount) {
        this.user = user;
        this.products = products;
        this.numberOfProducts = numberOfProducts;
        this.account = account;
        this.totalFee = totalFee;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
