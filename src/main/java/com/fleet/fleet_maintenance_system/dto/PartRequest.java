package com.fleet.fleet_maintenance_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class PartRequest {
    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Part number can't be blank")
    private String partNumber;

    @PositiveOrZero(message = "Stock Quantity must be greater or equal to zero")
    private int stockQuantity;
    @PositiveOrZero(message = "Reorder threshold must be greater or equal to zero")
    private int reorderThreshold;
    @Positive(message = "Unit cost must be positive")
    private BigDecimal unitCost;
    private Long supplierId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
