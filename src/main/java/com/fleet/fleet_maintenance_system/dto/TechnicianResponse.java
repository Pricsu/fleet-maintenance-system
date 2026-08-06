package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.Technician;

public class TechnicianResponse {

    private Long id;
    private String fullName;
    private String email;
    private String specialty;

    public TechnicianResponse(Long id, String fullName, String email, String specialty) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.specialty = specialty;
    }

    public static TechnicianResponse fromEntity(Technician technician){
        return new TechnicianResponse(
            technician.getId(),
            technician.getFullName(),
            technician.getEmail(),
            technician.getSpecialty()
        );
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getEmail() {
        return email;
    }
}
