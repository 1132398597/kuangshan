package com.mining.controller;

import com.mining.entity.Employee;
import com.mining.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Employee> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<List<Employee>> findByEnterprise(@PathVariable Long enterpriseId) {
        return ResponseEntity.ok(service.findByEnterprise(enterpriseId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> findByStatus(@PathVariable Employee.EmployeeStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> findByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(service.findByDepartment(department));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(service.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{employeeId}/assign/{enterpriseId}")
    public ResponseEntity<Void> assignToEnterprise(@PathVariable Long employeeId, @PathVariable Long enterpriseId) {
        service.assignToEnterprise(employeeId, enterpriseId);
        return ResponseEntity.noContent().build();
    }
}
