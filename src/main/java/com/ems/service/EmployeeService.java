package com.ems.service;

import com.ems.dto.EmployeeDTO;
import com.ems.entity.Employee;
import com.ems.exception.ResourceNotFoundException;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // ✅ SAVE
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        Employee employee = mapToEntity(dto);

        int score = 0;

        if (employee.getSalary() < 30000) score++;
        if (employee.getPerformanceRating() <= 2) score++;
        if (employee.getJobSatisfaction() <= 2) score++;
        if (employee.getYearsAtCompany() < 2) score++;

        if (score >= 3) {
            employee.setRiskLevel("HIGH");
        } else if (score == 2) {
            employee.setRiskLevel("MEDIUM");
        } else {
            employee.setRiskLevel("LOW");
        }

        Employee saved = repository.save(employee);

        return mapToDTO(saved);
    }

    // ✅ GET ALL
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ UPDATE
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {

        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        existing.setName(dto.getName());
        existing.setDepartment(dto.getDepartment());
        existing.setSalary(dto.getSalary());
        existing.setPerformanceRating(dto.getPerformanceRating());
        existing.setJobSatisfaction(dto.getJobSatisfaction());
        existing.setYearsAtCompany(dto.getYearsAtCompany());

        return saveEmployee(mapToDTO(existing));
    }

    // ✅ DELETE
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    // ✅ HIGH RISK
    public List<EmployeeDTO> getHighRiskEmployees() {
        return repository.findByRiskLevel("HIGH")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ DASHBOARD
    public Map<String, Long> getRiskCount() {

        Map<String, Long> map = new HashMap<>();

        map.put("HIGH", repository.countByRiskLevel("HIGH"));
        map.put("MEDIUM", repository.countByRiskLevel("MEDIUM"));
        map.put("LOW", repository.countByRiskLevel("LOW"));

        return map;
    }

    // ✅ SEARCH BY DEPARTMENT
    public List<EmployeeDTO> getByDepartment(String dept) {
        return repository.findByDepartment(dept)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔁 ENTITY → DTO
    private EmployeeDTO mapToDTO(Employee emp) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setDepartment(emp.getDepartment());
        dto.setSalary(emp.getSalary());
        dto.setPerformanceRating(emp.getPerformanceRating());
        dto.setJobSatisfaction(emp.getJobSatisfaction());
        dto.setYearsAtCompany(emp.getYearsAtCompany());
        dto.setRiskLevel(emp.getRiskLevel());

        return dto;
    }

    // 🔁 DTO → ENTITY
    private Employee mapToEntity(EmployeeDTO dto) {

        Employee emp = new Employee();

        emp.setId(dto.getId());
        emp.setName(dto.getName());
        emp.setDepartment(dto.getDepartment());
        emp.setSalary(dto.getSalary());
        emp.setPerformanceRating(dto.getPerformanceRating());
        emp.setJobSatisfaction(dto.getJobSatisfaction());
        emp.setYearsAtCompany(dto.getYearsAtCompany());

        return emp;
    }

    public Page<EmployeeDTO> getEmployeesWithPagination(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Employee> employeePage = repository.findAll(pageable);

        return employeePage.map(this::mapToDTO);
    }
}