package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String department;

    private double salary;

    private String riskLevel;

    private int performanceRating;

    private int yearsAtCompany;

    private int jobSatisfaction;
}