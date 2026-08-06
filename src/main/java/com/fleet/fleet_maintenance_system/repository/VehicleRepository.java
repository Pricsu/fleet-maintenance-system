package com.fleet.fleet_maintenance_system.repository;

import com.fleet.fleet_maintenance_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
