package com.ems.repository;

import com.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    long countByRiskLevel(String riskLevel);
    List<Employee> findByDepartment(String department);

    List<Employee> findByRiskLevel(String high);
}