package com.fleet.fleet_maintenance_system.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


import java.time.LocalDate;

public class VehicleRequest {

    @NotBlank(message = "License plate can't be blak")
    private String licensePlate;

    @NotBlank(message = "Make can't be blak")
    private String make;

    @NotBlank(message = "Model name can't be blak")
    private String model;

    @Positive(message = "Year must be positive")
    private int year;

    @PositiveOrZero(message = "Mileage must be greater or equal to zero")
    private int mileageKm;

    private LocalDate nextServiceDue;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileageKm() {
        return mileageKm;
    }

    public void setMileageKm(int mileageKm) {
        this.mileageKm = mileageKm;
    }

    public LocalDate getNextServiceDue() {
        return nextServiceDue;
    }

    public void setNextServiceDue(LocalDate nextServiceDue) {
        this.nextServiceDue = nextServiceDue;
    }


}
