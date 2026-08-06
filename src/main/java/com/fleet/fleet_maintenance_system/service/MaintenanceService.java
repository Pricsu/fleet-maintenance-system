package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.MaintenanceRequest;
import com.fleet.fleet_maintenance_system.dto.MaintenanceResponse;
import com.fleet.fleet_maintenance_system.dto.PartResponse;
import com.fleet.fleet_maintenance_system.entity.MaintenanceRecord;
import com.fleet.fleet_maintenance_system.entity.Part;
import com.fleet.fleet_maintenance_system.repository.MaintenanceRecordRepository;
import com.fleet.fleet_maintenance_system.repository.PartRepository;
import com.fleet.fleet_maintenance_system.repository.TechnicianRepository;
import com.fleet.fleet_maintenance_system.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {
    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final TechnicianRepository technicianRepository;
    private final PartRepository partRepository;

    public MaintenanceService(MaintenanceRecordRepository maintenanceRecordRepository, VehicleRepository vehicleRepository, TechnicianRepository technicianRepository, PartRepository partRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
        this.vehicleRepository = vehicleRepository;
        this.technicianRepository = technicianRepository;
        this.partRepository = partRepository;
    }

    public List<MaintenanceResponse> findAll(){
        return maintenanceRecordRepository.findAll().stream().map(MaintenanceResponse::fromEntity).toList();
    }

    private MaintenanceRecord findMaintenanceById(Long id){
        return maintenanceRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found: " + id));
    }

    public MaintenanceResponse findById(Long id){
        return MaintenanceResponse.fromEntity(findMaintenanceById(id));
    }

    @Transactional
    public MaintenanceResponse create(MaintenanceRequest request){
        MaintenanceRecord maintenance = new MaintenanceRecord();
        maintenance.setVehicle(vehicleRepository.findById(request.getVehicleId()).
                orElseThrow(() -> new IllegalArgumentException("Vehicle not found: "+ request.getVehicleId())));
        maintenance.setTechnician(technicianRepository.findById(request.getTechnicianId()).orElseThrow(() -> new IllegalArgumentException("Technician not found: " + request.getTechnicianId())));

        maintenance.setDescription(request.getDescription());
        maintenance.setLaborCost(request.getLaborCost());
        maintenance.setServiceDate(request.getServiceDate() != null ? request.getServiceDate() : LocalDate.now());
        List<MaintenanceRecord.PartUsage> partUsageResponses = new ArrayList<>();
        for(MaintenanceRequest.PartUsageRequest partUsed : request.getPartsUsed()){
            Part part = partRepository.findById(partUsed.getPartId())
                    .orElseThrow(() -> new IllegalArgumentException("No part found"));
            if (!(part.getStockQuantity() >= partUsed.getQuantity())){
                throw new IllegalStateException("There are no parts available");
            }
            part.setStockQuantity(part.getStockQuantity() - partUsed.getQuantity());
            partRepository.save(part);
            partUsageResponses.add(new MaintenanceRecord.PartUsage(partUsed.getPartId(), partUsed.getQuantity()));
        }
        maintenance.setPartsUsed(partUsageResponses);
        maintenanceRecordRepository.save(maintenance);
        return MaintenanceResponse.fromEntity(maintenance);
    }

    public Map<String, BigDecimal> costPerVehicle(){
        return maintenanceRecordRepository.findAll().stream()
                .collect(Collectors.groupingBy(record -> record.getVehicle().getLicensePlate(),
                        Collectors.reducing(BigDecimal.ZERO, MaintenanceRecord::getLaborCost,BigDecimal::add)));
    }
}
