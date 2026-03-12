package com.mining.controller;

import com.mining.entity.MineEnterprise;
import com.mining.service.MineEnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprises")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MineEnterpriseController {

    private final MineEnterpriseService service;

    @GetMapping
    public ResponseEntity<List<MineEnterprise>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MineEnterprise> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<MineEnterprise> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MineEnterprise>> findByStatus(@PathVariable MineEnterprise.EnterpriseStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<MineEnterprise> create(@RequestBody MineEnterprise enterprise) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(enterprise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MineEnterprise> update(@PathVariable Long id, @RequestBody MineEnterprise enterprise) {
        return ResponseEntity.ok(service.update(id, enterprise));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
