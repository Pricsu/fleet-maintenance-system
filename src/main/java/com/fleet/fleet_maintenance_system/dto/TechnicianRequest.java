package com.fleet.fleet_maintenance_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TechnicianRequest {

    @NotBlank(message = "Full name can't be blak")
    private String fullName;

    @Email(message = "Email must respect a valid email format")
    @NotBlank(message = "Email can't be blak")
    private String email;

    @NotBlank(message = "Specialty name can't be blak")
    private String specialty;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
