package com.example.dbapp.model.entity;

import java.util.Date;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Long customerId;
    private double totalAmount;
    private Date orderDate;
    private String status;

    public Order() {
    }

    public Order(Long customerId, double totalAmount, Date orderDate, String status) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

