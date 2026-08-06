package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.MaintenanceRecord;
import com.fleet.fleet_maintenance_system.entity.Technician;
import com.fleet.fleet_maintenance_system.entity.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRequest {
    @NotNull(message = "vehicle id cannot be null")
    private Long vehicleId;
    @NotNull(message = "technician id cannot be null")
    private Long technicianId;
    private LocalDate serviceDate;
    private String description;
    private BigDecimal laborCost;
    private List<PartUsageRequest> partsUsed;

    public static class PartUsageRequest{
        private Long partId;
        private int quantity;

        public Long getPartId() {
            return partId;
        }

        public void setPartId(Long partId) {
            this.partId = partId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public List<PartUsageRequest> getPartsUsed() {
        return partsUsed;
    }

    public void setPartsUsed(List<PartUsageRequest> partsUsed) {
        this.partsUsed = partsUsed;
    }
}
