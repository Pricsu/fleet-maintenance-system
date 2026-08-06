package com.fleet.fleet_maintenance_system;

import com.fleet.fleet_maintenance_system.entity.Vehicle;
import com.fleet.fleet_maintenance_system.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FleetMaintenanceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FleetMaintenanceSystemApplication.class, args);
	}

}
