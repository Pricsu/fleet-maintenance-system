package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.Vehicle;

import java.time.LocalDate;

public class VehicleResponse {

    private Long id;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private int mileageKm;
    private LocalDate nextServiceDue;

    public VehicleResponse(Long id, String licensePlate, String make, String model, int year, int mileageKm, LocalDate nextServiceDue) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileageKm = mileageKm;
        this.nextServiceDue = nextServiceDue;
    }

    public static VehicleResponse fromEntity(Vehicle vehicle){
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getMileageKm(),
                vehicle.getNextServiceDue()
        );
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileageKm() {
        return mileageKm;
    }

    public LocalDate getNextServiceDue() {
        return nextServiceDue;
    }
}
