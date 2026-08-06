package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.PartResponse;
import com.fleet.fleet_maintenance_system.dto.VehicleResponse;
import com.fleet.fleet_maintenance_system.service.MaintenanceService;
import com.fleet.fleet_maintenance_system.service.PartService;
import com.fleet.fleet_maintenance_system.service.VehicleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    private final PartService partService;
    private final VehicleService vehicleService;
    private final MaintenanceService maintenanceService;

    public ReportController(PartService partService, VehicleService vehicleService, MaintenanceService maintenanceService) {
        this.partService = partService;
        this.vehicleService = vehicleService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/api/parts/low-stock")
    public List<PartResponse> getLowStockParts() {
        return partService.findLowStock();
    }

    @GetMapping("/api/vehicles/due-for-service")
    public List<VehicleResponse> getVehiclesDueForService(){
        return vehicleService.getVehiclesDueForService();
    }

    @GetMapping("/api/reports/cost-per-vehicle")
    public Map<String, BigDecimal> getCostPerVehicle() {
        return maintenanceService.costPerVehicle();
    }
}
