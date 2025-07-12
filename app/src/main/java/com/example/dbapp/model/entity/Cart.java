package com.example.dbapp.model.entity;

import androidx.room.Entity;
import java.math.BigDecimal;
import androidx.room.PrimaryKey;

@Entity(tableName = "carts")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Long customerId;
    BigDecimal discountAmount;
    BigDecimal tipAmount;

    public Cart() {
    }

    public Cart(Long id, Long customerId, BigDecimal discountAmount, BigDecimal tipAmount) {
        this.id = id;
        this.customerId = customerId;
        this.discountAmount = discountAmount;
        this.tipAmount = tipAmount;
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

    public BigDecimal getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(BigDecimal tipAmount) {
        this.tipAmount = tipAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

}
