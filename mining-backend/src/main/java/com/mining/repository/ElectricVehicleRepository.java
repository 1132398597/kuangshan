package com.mining.repository;

import com.mining.entity.ElectricVehicle;
import com.mining.entity.MineEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ElectricVehicleRepository extends JpaRepository<ElectricVehicle, Long> {

    Optional<ElectricVehicle> findByVehicleCode(String vehicleCode);

    List<ElectricVehicle> findByEnterprise(MineEnterprise enterprise);

    List<ElectricVehicle> findByStatus(ElectricVehicle.VehicleStatus status);

    List<ElectricVehicle> findByBrand(String brand);

    boolean existsByVehicleCode(String vehicleCode);
}
