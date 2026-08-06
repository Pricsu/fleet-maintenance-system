package com.fleet.fleet_maintenance_system.repository;

import com.fleet.fleet_maintenance_system.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {
}
