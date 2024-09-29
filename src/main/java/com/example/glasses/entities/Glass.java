package com.example.glasses.entities;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "glasses")
public class Glass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice; // ціна закупки

    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice; // ціна продажу

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity; // кількість в наявності

    @Column(name = "sold_quantity", nullable = false)
    private int soldQuantity; // кількість проданих окулярів

    @Column(name = "image_url")
    private String imageUrl; // посилання на фото моделі

    @Column(name = "markup_percentage")
    private Double markupPercentage; // відсоток націнки
    @Column(name = "ideal_stock")
    private Integer idealStock;


    public Glass() {
    }

    public Glass(String modelName, BigDecimal purchasePrice, BigDecimal salePrice, int stockQuantity, int soldQuantity, String imageUrl, Double markupPercentage, Integer idealStock) {
        this.modelName = modelName;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = soldQuantity;
        this.imageUrl = imageUrl;
        this.markupPercentage = markupPercentage;
        this.idealStock = (idealStock != null) ? idealStock : 0;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIdealStock() {
        return idealStock != null ? idealStock : 0;
    }

    public void setIdealStock(Integer idealStock) {
        this.idealStock = idealStock;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getMarkupPercentage() {
        return markupPercentage;
    }

    public void setMarkupPercentage(Double markupPercentage) {
        this.markupPercentage = markupPercentage;
    }

    public BigDecimal getProfit() {
        return salePrice.subtract(purchasePrice).multiply(BigDecimal.valueOf(soldQuantity));
    }

    public BigDecimal getReplenishmentCost() {
        return purchasePrice.multiply(BigDecimal.valueOf(stockQuantity));
    }
}
