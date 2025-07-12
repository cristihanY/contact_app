package com.example.dbapp.model.dto;

import androidx.annotation.NonNull;

import com.example.dbapp.model.entity.Product;

public class CartItemDto {
    Long id;
    Long cartId;
    Long productId;
    String name;
    Integer quantity;
    Double price;
    Product product;

    public CartItemDto() {
    }

    public CartItemDto(Long id, Long cartId, Long productId, String name,
                       Integer quantity, Double price, Product product) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @NonNull
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + (product != null ? product.getName() : "null") +
                '}';
    }

}
