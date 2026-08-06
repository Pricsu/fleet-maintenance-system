package com.fleet.fleet_maintenance_system.service;


import com.fleet.fleet_maintenance_system.FleetMaintenanceSystemApplication;
import com.fleet.fleet_maintenance_system.dto.MaintenanceRequest;
import com.fleet.fleet_maintenance_system.dto.MaintenanceResponse;
import com.fleet.fleet_maintenance_system.entity.Part;
import com.fleet.fleet_maintenance_system.entity.Technician;
import com.fleet.fleet_maintenance_system.entity.Vehicle;

import com.fleet.fleet_maintenance_system.repository.PartRepository;
import com.fleet.fleet_maintenance_system.repository.TechnicianRepository;
import com.fleet.fleet_maintenance_system.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;


import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@ActiveProfiles("test")
public class MaintenanceServiceIntegrationTest {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private MaintenanceService maintenanceService;

    private Vehicle vehicle;
    private Technician technician;
    private Part brakePad;

    @BeforeEach
    void setUp(){
        vehicle = new Vehicle();
        vehicle.setMileageKm(100000);
        vehicle.setLicensePlate("MS-12-WWW");
        vehicle.setMake("audi");
        vehicle.setModel("a5");
        vehicle.setYear(2014);
        vehicle = vehicleRepository.save(vehicle);

        technician = new Technician();
        technician.setFullName("Alfred");
        technician.setEmail("elekesalfred@gmail.com");
        technician.setSpecialty("IT");
        technician = technicianRepository.save(technician);

        brakePad = new Part();
        brakePad.setReorderThreshold(3);
        brakePad.setStockQuantity(7);
        brakePad.setPartNumber("B1");
        brakePad.setName("brake pad");
        brakePad.setUnitCost(new BigDecimal("25.00"));
        brakePad = partRepository.save(brakePad);
    }

    @Test
    void create_deductStockCorrectly(){

        MaintenanceRequest request = new MaintenanceRequest();
        request.setVehicleId(vehicle.getId());
        request.setTechnicianId(technician.getId());
        request.setLaborCost(new BigDecimal("100.00"));
        request.setDescription("Replaced front brake pads");

        MaintenanceRequest.PartUsageRequest usage = new MaintenanceRequest.PartUsageRequest();
        usage.setPartId(brakePad.getId());
        usage.setQuantity(2);
        request.setPartsUsed(List.of(usage));

        MaintenanceResponse response = maintenanceService.create(request);

        assertThat(response.getId()).isNotNull();

        Part updatePart = partRepository.findById(brakePad.getId()).orElseThrow();
        assertThat(updatePart.getStockQuantity()).isEqualTo(5);
    }

    @Test
    void create_deductStockIncorrectly(){
        MaintenanceRequest request = new MaintenanceRequest();
        request.setVehicleId(vehicle.getId());
        request.setTechnicianId(technician.getId());
        request.setLaborCost(new BigDecimal("100.00"));
        request.setDescription("Replaced front brake pads");

        MaintenanceRequest.PartUsageRequest usage1 = new MaintenanceRequest.PartUsageRequest();
        usage1.setPartId(brakePad.getId());
        usage1.setQuantity(2);
        MaintenanceRequest.PartUsageRequest usage2 = new MaintenanceRequest.PartUsageRequest();
        usage2.setPartId(brakePad.getId());
        usage2.setQuantity(99999);
        request.setPartsUsed(List.of(usage1, usage2));

        assertThatThrownBy(() -> maintenanceService.create(request))
                .isInstanceOf(IllegalStateException.class);

        Part updatedPart = partRepository.findById(brakePad.getId()).orElseThrow();
        assertThat(updatedPart.getStockQuantity()).isEqualTo(7);
    }
}
