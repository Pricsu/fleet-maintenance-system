package com.fleet.fleet_maintenance_system.entity;

import com.fleet.fleet_maintenance_system.dto.PartResponse;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private Technician technician;

    private LocalDate serviceDate;
    private String description;
    private BigDecimal laborCost;

    @ElementCollection
    @CollectionTable(name = "maintenance_parts_used", joinColumns = @JoinColumn(name = "maintenance_record_id"))
    private List<PartUsage> partsUsed = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
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

    public List<PartUsage> getPartsUsed() {
        return partsUsed;
    }

    public void setPartsUsed(List<PartUsage> partsUsed) {
        this.partsUsed = partsUsed;
    }

    @Embeddable
    public static class PartUsage{

        @Column(name = "part_id")
        private Long partId;
        private int quantity;

        public PartUsage(Long partId, int quantity) {
            this.partId = partId;
            this.quantity = quantity;
        }

        public PartUsage() {

        }

        public Long getPartId() {
            return partId;
        }

        public int getQuantity() {
            return quantity;
        }
    }


}


