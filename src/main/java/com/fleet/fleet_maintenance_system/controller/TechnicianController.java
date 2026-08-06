package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.TechnicianRequest;
import com.fleet.fleet_maintenance_system.dto.TechnicianResponse;
import com.fleet.fleet_maintenance_system.entity.Technician;
import com.fleet.fleet_maintenance_system.service.TechnicianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TechnicianController {
    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping("/api/technicians")
    public List<TechnicianResponse> getTechnicians(){
        return technicianService.findAll();
    }

    @GetMapping("/api/technicians/{id}")
    public TechnicianResponse getTechnicianById( @PathVariable Long id){
        return technicianService.findById(id);
    }

    @PostMapping("/api/technicians")
    public TechnicianResponse createTechnician(@Valid @RequestBody TechnicianRequest technicianRequest){
        return technicianService.create(technicianRequest);
    }

    @PutMapping("/api/technicians/{id}")
    public TechnicianResponse updateTechnician(@Valid @RequestBody TechnicianRequest technicianRequest, @Valid @PathVariable Long id){
        return technicianService.update(technicianRequest, id);
    }

    @DeleteMapping("/api/technicians/{id}")
    public ResponseEntity<Void> deleteTechnician( @PathVariable Long id){
        technicianService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
