package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.VehicleRequest;
import com.fleet.fleet_maintenance_system.dto.VehicleResponse;
import com.fleet.fleet_maintenance_system.entity.Vehicle;
import com.fleet.fleet_maintenance_system.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping("/api/vehicles")
    public List<VehicleResponse> getVehicles(){
        return vehicleService.findAll();
    }

    @GetMapping("/api/vehicles/{id}")
    public VehicleResponse getVehicleById( @PathVariable Long id){
        return vehicleService.findById(id);
    }

    @PostMapping("/api/vehicles")
    public VehicleResponse createVehicle(@Valid @RequestBody VehicleRequest request ) {
        return vehicleService.create(request);
    }

    @PutMapping("/api/vehicles/{id}")
    public VehicleResponse updateVehicle( @PathVariable Long id, @Valid @RequestBody VehicleRequest request){
        return vehicleService.update(id, request);
    }

    @DeleteMapping("/api/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle( @PathVariable Long id){
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
