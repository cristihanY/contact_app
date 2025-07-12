package com.example.dbapp.model.dto;

import java.math.BigDecimal;

public class CartSummary {
    BigDecimal subTotal;
    BigDecimal taxAmount;
    BigDecimal tipAmount;
    BigDecimal discountAmount;
    BigDecimal total;
    BigDecimal change;

    public CartSummary() {
    }

    public CartSummary(BigDecimal subTotal, BigDecimal taxAmount,
                       BigDecimal tipAmount, BigDecimal discountAmount,
                       BigDecimal total, BigDecimal change) {
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.tipAmount = tipAmount;
        this.discountAmount = discountAmount;
        this.total = total;
        this.change = change;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
