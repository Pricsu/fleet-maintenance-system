package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.SupplierRequest;
import com.fleet.fleet_maintenance_system.dto.SupplierResponse;
import com.fleet.fleet_maintenance_system.entity.Supplier;
import com.fleet.fleet_maintenance_system.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierResponse> findAll(){
        return supplierRepository.findAll().stream().map(SupplierResponse::fromEntity).toList();
    }

    private Supplier findSupplierById(Long id){
        return supplierRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Supplier not found: " + id));
    }
    public SupplierResponse findById(Long id){
        return SupplierResponse.fromEntity(findSupplierById(id));
    }

    private void applyRequest(Supplier supplier, SupplierRequest request){
        supplier.setName(request.getName());
        supplier.setContactEmail(request.getContactEmail());
        supplier.setPhone(request.getPhone());
    }

    public SupplierResponse create(SupplierRequest request){
        Supplier supplier = new Supplier();
        applyRequest(supplier, request);
        supplierRepository.save(supplier);
        return SupplierResponse.fromEntity(supplier);
    }

    public SupplierResponse update(Long id, SupplierRequest request){
        Supplier supplier = findSupplierById(id);
        applyRequest(supplier, request);
        supplierRepository.save(supplier);
        return SupplierResponse.fromEntity(supplier);
    }

    public void delete(Long id){
        Supplier supplier = findSupplierById(id);
        supplierRepository.delete(supplier);
    }
}
