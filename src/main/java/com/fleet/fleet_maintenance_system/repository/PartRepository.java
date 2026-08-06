package com.fleet.fleet_maintenance_system.repository;

import com.fleet.fleet_maintenance_system.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
