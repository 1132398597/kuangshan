package com.mining.service;

import com.mining.entity.Employee;
import com.mining.entity.MineEnterprise;
import com.mining.repository.EmployeeRepository;
import com.mining.repository.MineEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MineEnterpriseRepository enterpriseRepository;

    public Employee create(Employee employee) {
        if (employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("员工编号已存在");
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("员工不存在"));
        
        if (!existing.getEmployeeCode().equals(employee.getEmployeeCode()) &&
            employeeRepository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new IllegalArgumentException("员工编号已存在");
        }
        
        existing.setEmployeeCode(employee.getEmployeeCode());
        existing.setEmployeeName(employee.getEmployeeName());
        existing.setDepartment(employee.getDepartment());
        existing.setPosition(employee.getPosition());
        existing.setLocation(employee.getLocation());
        existing.setPhone(employee.getPhone());
        existing.setJoinDate(employee.getJoinDate());
        existing.setStatus(employee.getStatus());
        existing.setRemarks(employee.getRemarks());
        
        return employeeRepository.save(existing);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("员工不存在"));
    }

    @Transactional(readOnly = true)
    public Employee findByCode(String employeeCode) {
        return employeeRepository.findByEmployeeCode(employeeCode)
            .orElseThrow(() -> new RuntimeException("员工不存在"));
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Employee> findByEnterprise(Long enterpriseId) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return employeeRepository.findByEnterprise(enterprise);
    }

    @Transactional(readOnly = true)
    public List<Employee> findByStatus(Employee.EmployeeStatus status) {
        return employeeRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Employee> findByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public void assignToEnterprise(Long employeeId, Long enterpriseId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("员工不存在"));
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        
        employee.setEnterprise(enterprise);
        employeeRepository.save(employee);
    }
}
