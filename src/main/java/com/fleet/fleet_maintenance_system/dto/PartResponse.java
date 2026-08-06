package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.Part;

import java.math.BigDecimal;

public class PartResponse {

    private Long id;
    private String name;
    private String partNumber;
    private int stockQuantity;
    private int reorderThreshold;
    private BigDecimal unitCost;
    private SupplierResponse supplier;

    public PartResponse(Long id, String name, String partNumber, int stockQuantity, int reorderThreshold, BigDecimal unitCost, SupplierResponse supplier) {
        this.id = id;
        this.name = name;
        this.partNumber = partNumber;
        this.stockQuantity = stockQuantity;
        this.reorderThreshold = reorderThreshold;
        this.unitCost = unitCost;
        this.supplier = supplier;
    }

    public static PartResponse fromEntity(Part part){
        return new PartResponse(
            part.getId(),
            part.getName(),
            part.getPartNumber(),
            part.getStockQuantity(),
            part.getReorderThreshold(),
            part.getUnitCost(),
            SupplierResponse.fromEntity(part.getSupplier())
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public SupplierResponse getSupplier() {
        return supplier;
    }
}
