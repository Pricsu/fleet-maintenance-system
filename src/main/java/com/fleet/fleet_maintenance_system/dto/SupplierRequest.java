package com.fleet.fleet_maintenance_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;


public class SupplierRequest {
    @NotBlank(message = "Name can't be blak")
    private String name;

    @NotBlank(message = "Email can't be blak")
    @Email(message = "Email is not valid")
    private String contactEmail;

    @NotBlank(message = "Phone can't be blak")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
