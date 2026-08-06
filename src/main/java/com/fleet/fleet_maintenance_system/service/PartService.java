package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.PartRequest;
import com.fleet.fleet_maintenance_system.dto.PartResponse;
import com.fleet.fleet_maintenance_system.entity.Part;
import com.fleet.fleet_maintenance_system.repository.PartRepository;
import com.fleet.fleet_maintenance_system.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;


    public PartService(PartRepository partRepository, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<PartResponse> findAll(){
        return partRepository.findAll().stream()
                .map(PartResponse::fromEntity).toList();
    }

    private Part findPartById(Long id){
        return partRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Part not found: " + id ));
    }

    public PartResponse findById(Long id){
        return PartResponse.fromEntity(findPartById(id));
    }

    public PartResponse create(PartRequest request){
        Part part = new Part();
        applyRequest(part, request);
        partRepository.save(part);
        return PartResponse.fromEntity(part);
    }

    public PartResponse update(Long id, PartRequest request){
        Part part = findPartById(id);
        applyRequest(part, request);
        partRepository.save(part);
        return PartResponse.fromEntity(part);
    }

    private void applyRequest(Part part, PartRequest request){
        part.setName(request.getName());
        part.setPartNumber(request.getPartNumber());
        part.setStockQuantity(request.getStockQuantity());
        part.setReorderThreshold(request.getReorderThreshold());
        part.setUnitCost(request.getUnitCost());
        part.setSupplier(supplierRepository.findById(request.getSupplierId()).
                orElseThrow(() -> new IllegalArgumentException("Supplier Not found: " + request.getSupplierId())));
    }

    public void delete(Long id){
        partRepository.delete(findPartById(id));
    }

    public List<PartResponse> findLowStock(){
        return partRepository.findAll().stream()
                .filter(part -> part.getStockQuantity() <= part.getReorderThreshold())
                .map(PartResponse::fromEntity).toList();
    }


}
