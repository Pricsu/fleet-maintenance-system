package com.fleet.fleet_maintenance_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String make;
    private String model;
    private int mileageKm;
    private LocalDate nextServiceDue;
    @Column(name = "vehicle_year")
    private int year;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
