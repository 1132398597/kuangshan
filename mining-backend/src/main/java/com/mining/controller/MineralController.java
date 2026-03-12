package com.mining.controller;

import com.mining.entity.Mineral;
import com.mining.service.MineralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/minerals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MineralController {

    private final MineralService service;

    @GetMapping
    public ResponseEntity<List<Mineral>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mineral> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Mineral> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<List<Mineral>> findByEnterprise(@PathVariable Long enterpriseId) {
        return ResponseEntity.ok(service.findByEnterprise(enterpriseId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Mineral>> findByStatus(@PathVariable Mineral.MiningStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Mineral> create(@RequestBody Mineral mineral) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(mineral));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mineral> update(@PathVariable Long id, @RequestBody Mineral mineral) {
        return ResponseEntity.ok(service.update(id, mineral));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
