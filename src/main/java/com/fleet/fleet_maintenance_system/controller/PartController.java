package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.PartRequest;
import com.fleet.fleet_maintenance_system.dto.PartResponse;
import com.fleet.fleet_maintenance_system.service.PartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartController{

    private final PartService partService;


    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/api/parts")
    public List<PartResponse> findAllParts(){
        return partService.findAll();
    }

    @GetMapping("/api/parts/{id}")
    public PartResponse findPartById(@PathVariable Long id){
        return partService.findById(id);
    }

    @PostMapping("/api/parts")
    public PartResponse createPart(@Valid @RequestBody PartRequest request){
        return partService.create(request);
    }

    @PutMapping("/api/parts/{id}")
    public PartResponse updatePart(@PathVariable Long id, @Valid @RequestBody PartRequest request){
        return partService.update(id, request);
    }

    @DeleteMapping("/api/parts/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id){
        partService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
