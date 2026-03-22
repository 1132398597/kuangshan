package com.mining.service;

import com.mining.entity.ElectricVehicle;
import com.mining.entity.MineEnterprise;
import com.mining.repository.ElectricVehicleRepository;
import com.mining.repository.MineEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ElectricVehicleService {

    private final ElectricVehicleRepository vehicleRepository;
    private final MineEnterpriseRepository enterpriseRepository;

    public ElectricVehicle create(ElectricVehicle vehicle) {
        if (vehicleRepository.existsByVehicleCode(vehicle.getVehicleCode())) {
            throw new IllegalArgumentException("车辆编号已存在");
        }
        return vehicleRepository.save(vehicle);
    }

    public ElectricVehicle update(Long id, ElectricVehicle vehicle) {
        ElectricVehicle existing = vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("车辆不存在"));

        if (!existing.getVehicleCode().equals(vehicle.getVehicleCode()) &&
            vehicleRepository.existsByVehicleCode(vehicle.getVehicleCode())) {
            throw new IllegalArgumentException("车辆编号已存在");
        }

        existing.setVehicleCode(vehicle.getVehicleCode());
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setLicensePlate(vehicle.getLicensePlate());
        existing.setEnterprise(vehicle.getEnterprise());
        existing.setDriverName(vehicle.getDriverName());
        existing.setPurchaseDate(vehicle.getPurchaseDate());
        existing.setBatteryCapacity(vehicle.getBatteryCapacity());
        existing.setStatus(vehicle.getStatus());
        existing.setRemarks(vehicle.getRemarks());

        return vehicleRepository.save(existing);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ElectricVehicle findById(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("车辆不存在"));
    }

    @Transactional(readOnly = true)
    public ElectricVehicle findByCode(String vehicleCode) {
        return vehicleRepository.findByVehicleCode(vehicleCode)
            .orElseThrow(() -> new RuntimeException("车辆不存在"));
    }

    @Transactional(readOnly = true)
    public List<ElectricVehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ElectricVehicle> findByEnterprise(Long enterpriseId) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return vehicleRepository.findByEnterprise(enterprise);
    }

    @Transactional(readOnly = true)
    public List<ElectricVehicle> findByStatus(ElectricVehicle.VehicleStatus status) {
        return vehicleRepository.findByStatus(status);
    }

    public void assignToEnterprise(Long vehicleId, Long enterpriseId) {
        ElectricVehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new RuntimeException("车辆不存在"));
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));

        vehicle.setEnterprise(enterprise);
        vehicleRepository.save(vehicle);
    }
}
