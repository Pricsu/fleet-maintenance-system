package com.fleet.fleet_maintenance_system.repository;

import com.fleet.fleet_maintenance_system.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
}
