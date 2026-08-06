package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.VehicleRequest;
import com.fleet.fleet_maintenance_system.dto.VehicleResponse;
import com.fleet.fleet_maintenance_system.entity.Vehicle;
import com.fleet.fleet_maintenance_system.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleResponse> findAll(){
        return vehicleRepository.findAll().stream()
                .map(VehicleResponse::fromEntity).toList();
    }

    private Vehicle findVehicleById(Long id){
        return vehicleRepository.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException("Vehicle not found: " + id));
    }
    public VehicleResponse findById(Long id){
        return VehicleResponse.fromEntity(findVehicleById(id));
    }

    public VehicleResponse create(VehicleRequest request){
        Vehicle vehicle = new Vehicle();
        applyRequest(request, vehicle);
        vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(vehicle);
    }

    public VehicleResponse update(Long id, VehicleRequest request){
        Vehicle vehicle = findVehicleById(id);
        applyRequest(request, vehicle);
        vehicleRepository.save(vehicle);
        return VehicleResponse.fromEntity(vehicle);
    }

    public void delete(Long id){
        vehicleRepository.delete(findVehicleById(id));
    }

    private void applyRequest(VehicleRequest request, Vehicle vehicle) {
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setMake(request.getMake());
        vehicle.setMileageKm(request.getMileageKm());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setNextServiceDue(request.getNextServiceDue());
    }

    public List<VehicleResponse> getVehiclesDueForService(){
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getNextServiceDue() != null && !vehicle.getNextServiceDue().isAfter(LocalDate.now()))
                .map(VehicleResponse::fromEntity)
                .toList();
    }

}
