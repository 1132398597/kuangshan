package com.mining.controller;

import com.mining.entity.ElectricVehicle;
import com.mining.service.ElectricVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/electric-vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ElectricVehicleController {

    private final ElectricVehicleService service;

    @GetMapping
    public ResponseEntity<List<ElectricVehicle>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricVehicle> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ElectricVehicle> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<List<ElectricVehicle>> findByEnterprise(@PathVariable Long enterpriseId) {
        return ResponseEntity.ok(service.findByEnterprise(enterpriseId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ElectricVehicle>> findByStatus(@PathVariable ElectricVehicle.VehicleStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<ElectricVehicle> create(@RequestBody ElectricVehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElectricVehicle> update(@PathVariable Long id, @RequestBody ElectricVehicle vehicle) {
        return ResponseEntity.ok(service.update(id, vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{vehicleId}/assign/{enterpriseId}")
    public ResponseEntity<Void> assignToEnterprise(@PathVariable Long vehicleId, @PathVariable Long enterpriseId) {
        service.assignToEnterprise(vehicleId, enterpriseId);
        return ResponseEntity.noContent().build();
    }
}
