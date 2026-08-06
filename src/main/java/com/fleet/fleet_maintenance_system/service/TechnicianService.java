package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.TechnicianRequest;
import com.fleet.fleet_maintenance_system.dto.TechnicianResponse;

import com.fleet.fleet_maintenance_system.entity.Technician;

import com.fleet.fleet_maintenance_system.repository.TechnicianRepository;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;

    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public List<TechnicianResponse> findAll(){
        return technicianRepository.findAll().stream()
                .map(TechnicianResponse::fromEntity).toList();
    }

    private Technician findTechnicianById(Long id){
       return technicianRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Technician not found: " + id));
    }

    public TechnicianResponse findById(Long id){
        return TechnicianResponse.fromEntity(findTechnicianById(id));
    }

    public TechnicianResponse create(TechnicianRequest request){
        Technician technician = new Technician();
        applyRequest(request, technician);
        technicianRepository.save(technician);
        return TechnicianResponse.fromEntity(technician);
    }

    public TechnicianResponse update(TechnicianRequest request, Long id){
        Technician technician = findTechnicianById(id);
        applyRequest(request, technician);
        technicianRepository.save(technician);
        return TechnicianResponse.fromEntity(technician);
    }

    public void delete(Long id){
        technicianRepository.delete(findTechnicianById(id));
    }

    private void applyRequest(TechnicianRequest request, Technician technician){
        technician.setFullName(request.getFullName());
        technician.setEmail(request.getEmail());
        technician.setSpecialty(request.getSpecialty());

    }
}
