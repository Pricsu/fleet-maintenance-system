package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.MaintenanceRecord;

import io.micrometer.observation.Observation;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

public class MaintenanceResponse {

    private Long id;
    private VehicleResponse vehicle;
    private TechnicianResponse technician;
    private LocalDate serviceDate;
    private String description;
    private BigDecimal laborCost;
    private List<PartUsageResponse> partsUsed;

    public static class PartUsageResponse {
        private Long partId;
        private int quantity;

        public PartUsageResponse(Long partId, int quantity) {
            this.partId = partId;
            this.quantity = quantity;
        }

        public Long getPartId() {
            return partId;
        }

        public int getQuantity() {
            return quantity;
        }
    }


    public MaintenanceResponse(Long id, VehicleResponse vehicle, TechnicianResponse technician, LocalDate serviceDate, String description, BigDecimal laborCost, List<PartUsageResponse> partsUsed) {
        this.id = id;
        this.vehicle = vehicle;
        this.technician = technician;
        this.serviceDate = serviceDate;
        this.description = description;
        this.laborCost = laborCost;
        this.partsUsed = partsUsed;
    }

    public static MaintenanceResponse fromEntity(MaintenanceRecord maintenanceRecord){

        List<PartUsageResponse> parts = maintenanceRecord.getPartsUsed().stream()
                .map(usage -> new PartUsageResponse(usage.getPartId(), usage.getQuantity())).toList();

        return new MaintenanceResponse(
                maintenanceRecord.getId(),
                VehicleResponse.fromEntity(maintenanceRecord.getVehicle()),
                TechnicianResponse.fromEntity(maintenanceRecord.getTechnician()),
                maintenanceRecord.getServiceDate(),
                maintenanceRecord.getDescription(),
                maintenanceRecord.getLaborCost(),
                parts
                );
    }

    public Long getId() {
        return id;
    }

    public VehicleResponse getVehicle() {
        return vehicle;
    }

    public TechnicianResponse getTechnician() {
        return technician;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public List<PartUsageResponse> getPartsUsed() {
        return partsUsed;
    }
}
