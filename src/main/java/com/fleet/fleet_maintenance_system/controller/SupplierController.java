package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.SupplierRequest;
import com.fleet.fleet_maintenance_system.dto.SupplierResponse;
import com.fleet.fleet_maintenance_system.entity.Supplier;
import com.fleet.fleet_maintenance_system.service.SupplierService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/api/suppliers")
    public List<SupplierResponse> findAllSuppliers(){
        return supplierService.findAll();
    }

    @GetMapping("/api/suppliers/{id}")
    public SupplierResponse findById( @PathVariable Long id){
        return supplierService.findById(id);
    }

    @PostMapping("/api/suppliers")
    public SupplierResponse createSupplier(@Valid @RequestBody SupplierRequest request){
        return supplierService.create(request);
    }

    @PutMapping("/api/suppliers/{id}")
    public SupplierResponse updateSupplier( @PathVariable Long id, @Valid @RequestBody SupplierRequest request){
        return supplierService.update(id, request);
    }

    @DeleteMapping("/api/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier( @PathVariable Long id){
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
