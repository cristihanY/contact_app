package com.example.dbapp.model.entity;

import java.util.Date;
import java.math.BigDecimal;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.dbapp.converter.BigDecimalConverter;

@Entity(tableName = "products")
@TypeConverters({BigDecimalConverter.class})
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String img;
    private String barcode;
    private BigDecimal costPrice;
    private String description;
    private Date createdAt;


    public Product() {
    }

    public Product(Long categoryId, String name,
                   BigDecimal price, int quantity, String img,
                   String barcode, BigDecimal costPrice, String description,
                   Date createdAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.barcode = barcode;
        this.costPrice = costPrice;
        this.description = description;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
