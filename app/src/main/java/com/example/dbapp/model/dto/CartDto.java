package com.example.dbapp.model.dto;

import java.util.List;
import java.math.BigDecimal;

public class CartDto {
    Long id;
    Long customerId;
    BigDecimal discountAmount;
    BigDecimal tipAmount;
    List<CartItemDto> item;
    CartSummary summary;

    public CartDto() {
    }

    public CartDto(Long id, Long customerId, BigDecimal discountAmount,
                   BigDecimal tipAmount,
                   List<CartItemDto> item, CartSummary summary) {
        this.id = id;
        this.customerId = customerId;
        this.discountAmount = discountAmount;
        this.tipAmount = tipAmount;
        this.item = item;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(BigDecimal tipAmount) {
        this.tipAmount = tipAmount;
    }

    public List<CartItemDto> getItem() {
        return item;
    }

    public void setItem(List<CartItemDto> item) {
        this.item = item;
    }

    public CartSummary getSummary() {
        return summary;
    }

    public void setSummary(CartSummary summary) {
        this.summary = summary;
    }
}
