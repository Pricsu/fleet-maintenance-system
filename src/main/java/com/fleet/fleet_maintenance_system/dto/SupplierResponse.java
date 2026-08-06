package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.Supplier;

public class SupplierResponse {

    private Long id;
    private String name;
    private String contactEmail;
    private String phone;

    public SupplierResponse(Long id, String name, String contactEmail, String phone) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
        this.phone = phone;
    }

    public static SupplierResponse fromEntity(Supplier supplier){
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactEmail(),
                supplier.getPhone()
        );
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getContactEmail() { return contactEmail; }
    public String getPhone() { return phone; }
}
