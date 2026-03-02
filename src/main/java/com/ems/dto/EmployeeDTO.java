package com.ems.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Department is required")
    private String department;

    @Min(value = 10000, message = "Salary must be greater than 10000")
    private double salary;

    @Min(value = 1, message = "Performance rating must be at least 1")
    @Max(value = 5, message = "Performance rating max is 5")
    private int performanceRating;

    @Min(value = 1)
    @Max(value = 5)
    private int jobSatisfaction;

    @Min(value = 0)
    private int yearsAtCompany;

    private String riskLevel;
}