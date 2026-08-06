package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.MaintenanceRequest;
import com.fleet.fleet_maintenance_system.dto.MaintenanceResponse;
import com.fleet.fleet_maintenance_system.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MaintenanceController {

    private final MaintenanceService maintenanceService;


    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/api/maintenances")
    public List<MaintenanceResponse> findAllMaintenances(){
        return maintenanceService.findAll();
    }

    @GetMapping("/api/maintenances/{id}")
    public MaintenanceResponse findMaintenanceById(@PathVariable Long id){
        return maintenanceService.findById(id);
    }

    @PostMapping("/api/maintenances")
    public MaintenanceResponse createMaintenance(@Valid @RequestBody MaintenanceRequest request){
        return maintenanceService.create(request);
    }
}
