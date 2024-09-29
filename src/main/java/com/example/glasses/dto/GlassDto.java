package com.example.glasses.dto;

import java.math.BigDecimal;

public class GlassDto {
    private String modelName;
    private int stockQuantity;
    private int soldQuantity;
    private int oldSoldQuantity;
    private BigDecimal profit;
    private BigDecimal replenishmentCost;
    private BigDecimal restockingCost;
    private Integer idealStock;

    public GlassDto() {

    }
    public GlassDto (String modelName, int stockQuantity, int soldQuantity, int oldSoldQuantity, BigDecimal profit, BigDecimal replenishmentCost, BigDecimal restockingCost, Integer idealStock)
    {
        this.modelName = modelName;
        this.stockQuantity = stockQuantity;
        this.soldQuantity = soldQuantity;
        this.oldSoldQuantity = oldSoldQuantity;
        this.profit = profit;
        this.replenishmentCost = replenishmentCost;
        this.restockingCost = restockingCost;
        this.idealStock = idealStock;
    }
    public int getOldSoldQuantity() {
        return oldSoldQuantity;
    }

    public void setOldSoldQuantity(int oldSoldQuantity) {
        this.oldSoldQuantity = oldSoldQuantity;
    }
    public Integer getIdealStock() {
        return idealStock;
    }

    public void setIdealStock(Integer idealStock) {
        this.idealStock = idealStock;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public BigDecimal getRestockingCost() {
        return restockingCost;
    }

    public void setRestockingCost(BigDecimal restockingCost) {
        this.restockingCost = restockingCost;
    }
    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getReplenishmentCost() {
        return replenishmentCost;
    }

    public void setReplenishmentCost(BigDecimal replenishmentCost) {
        this.replenishmentCost = replenishmentCost;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
