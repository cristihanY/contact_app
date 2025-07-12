package com.example.dbapp.model.entity;

import java.util.Date;
import java.math.BigDecimal;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.dbapp.model.enums.OrderStatus;
import com.example.dbapp.model.enums.PaymentStatus;

@Entity(tableName = "orders")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Long customerId;
    private Integer orderNumber;
    private OrderStatus status;
    private String cancelReason;
    private BigDecimal subTotal;
    private BigDecimal discountAmount;
    private BigDecimal tipAmount;
    private BigDecimal tax;
    private BigDecimal total;
    private BigDecimal receivedAmount;
    private PaymentStatus paymentStatus;
    private Date created;

    public Order() {
    }

    public Order(long id, Long customerId, Integer orderNumber, OrderStatus status, String cancelReason,
                 BigDecimal subTotal, BigDecimal discountAmount, BigDecimal tipAmount, BigDecimal tax, BigDecimal total,
                 BigDecimal receivedAmount, PaymentStatus paymentStatus, Date created) {
        this.id = id;
        this.customerId = customerId;
        this.orderNumber = orderNumber;
        this.status = status;
        this.cancelReason = cancelReason;
        this.subTotal = subTotal;
        this.discountAmount = discountAmount;
        this.tipAmount = tipAmount;
        this.tax = tax;
        this.total = total;
        this.receivedAmount = receivedAmount;
        this.paymentStatus = paymentStatus;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
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

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

