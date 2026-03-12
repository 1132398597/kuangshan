package com.mining.repository;

import com.mining.entity.Employee;
import com.mining.entity.MineEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    List<Employee> findByEnterprise(MineEnterprise enterprise);

    List<Employee> findByStatus(Employee.EmployeeStatus status);

    List<Employee> findByDepartment(String department);

    boolean existsByEmployeeCode(String employeeCode);
}
