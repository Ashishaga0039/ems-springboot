package com.ems.controller;

import com.ems.dto.EmployeeDTO;
import com.ems.response.ApiResponse;
import com.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // ✅ SAVE
    @PostMapping
    public ApiResponse<EmployeeDTO> saveEmployee(@RequestBody @Valid EmployeeDTO dto) {

        EmployeeDTO savedEmployee = service.saveEmployee(dto);

        return new ApiResponse<>("Employee saved successfully", savedEmployee, 201);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id,
                                      @Valid @RequestBody EmployeeDTO dto) {
        return service.updateEmployee(id, dto);
    }

    // ✅ GET ALL
    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {

        return new ApiResponse<>("Employees fetched successfully",
                service.getAllEmployees(), 200);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

        service.deleteEmployee(id);

        return new ApiResponse<>("Employee deleted successfully", null, 200);
    }

    // ✅ HIGH RISK
    @GetMapping("/high-risk")
    public List<EmployeeDTO> getHighRiskEmployees() {
        return service.getHighRiskEmployees();
    }

    // ✅ DASHBOARD
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboard() {
        return service.getRiskCount();
    }

    // ✅ SEARCH BY DEPARTMENT
    @GetMapping("/department/{dept}")
    public List<EmployeeDTO> getByDepartment(@PathVariable String dept) {
        return service.getByDepartment(dept);
    }


    @GetMapping("/pagination")
    public ApiResponse<Page<EmployeeDTO>> getEmployeesWithPagination(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        return new ApiResponse<>("Employees fetched successfully",
                service.getEmployeesWithPagination(page, size, sortBy), 200);
    }

}